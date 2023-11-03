package com.example.templatesampleapp.ui.activmain

import android.net.Uri
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.ajithvgiri.searchdialog.SearchListItem
import com.ajithvgiri.searchdialog.SearchableDialog
import com.example.templatesampleapp.R
import com.example.templatesampleapp.base.BaseActivity
import com.example.templatesampleapp.databinding.ActivitySendnotifiBinding
import com.example.templatesampleapp.helper.Constants
import com.example.templatesampleapp.helper.showToast
import com.example.templatesampleapp.model.roomdb.NotificationRoomDbModel
import com.example.templatesampleapp.model.sendreq.Notification
import com.example.templatesampleapp.model.senreqtopic.SendTopicNotificationModel
import com.example.templatesampleapp.model.senreqtopic.Topic
import com.example.templatesampleapp.model.serviceaccount.ServiceAccountJsonModel
import com.google.auth.oauth2.GoogleCredentials
import com.google.gson.Gson
import com.infinum.dbinspector.DbInspector
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.apache.commons.io.FileUtils
import retrofit2.Call
import retrofit2.Response
import java.io.File
import java.io.InputStreamReader
import javax.security.auth.callback.Callback


@AndroidEntryPoint
class SendNotifiActivity : BaseActivity<ActivitySendnotifiBinding>(R.layout.activity_sendnotifi) {
    override val viewModel by viewModels<MainViewModel>()

    val fileServiveAccount by lazy {
        File(filesDir, "serviceaccount.json")
    }
    val projectId by lazy {
        if(fileServiveAccount.exists()){
           val reader= InputStreamReader(fileServiveAccount.inputStream())
            val jsonParser=Gson()
           val serviceClassModel= jsonParser.fromJson<ServiceAccountJsonModel>(reader, ServiceAccountJsonModel::class.java)
            showToast(serviceClassModel.project_id)
             serviceClassModel.project_id
        }else{
             ""
        }
    }
    var hashset: HashSet<String>? = null
    var activeToken = ""
    val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        // Handle the returned Uri

        try {
            val inStream=contentResolver.openInputStream(uri!!)!!
            org.apache.commons.io.FileUtils.copyToFile(
                inStream,
                File(filesDir, "serviceaccount.json")
            )

//            val googleCredentials = GoogleCredentials
//                .fromStream(inStream)
//                .createScoped(listOf(Constants.API_SCOPES))
//            googleCredentials.refresh()
//            showToast("Valid File "+googleCredentials.accessToken)
        } catch (e: Exception) {
            FileUtils.delete(File(filesDir, "serviceaccount.json"))
            showToast("Invalid file "+e.message)
        }


//        org.apache.commons.io.FileUtils.copfile(contentResolver.openInputStream(uri!!)!!,
//            File(this.filesDir,"serviceaccojt.json")
//        )
//        org.apache.commons.io.FileUtils.copyFile(contentResolver.openInputStream(uri!!)!!,
//            this.openFileOutput("serviceaccount.json", Context.MODE_PRIVATE))

//        FileUtils.copy(contentResolver.openInputStream(uri!!)!!,this.openFileOutput("serviceaccount.json",
//            Context.MODE_PRIVATE))


//        lifecycleScope.launch(Dispatchers.IO){
//            //save file to external internal storage
//            this@SendNotifiActivity.file
//
//
//            val googleCredentials = GoogleCredentials
//                .fromStream(contentResolver.openInputStream(uri!!))
//                .createScoped(listOf(Constants.API_SCOPES))
//            googleCredentials.refresh()
//            val token = googleCredentials.accessToken.tokenValue
//            Constants.setToken(this@SendNotifiActivity, token)
//            appendLog("token", "FreshToken "+ Constants.getBearerToken(this@SendNotifiActivity))
//
//        }


        showToast(uri?.toString() ?: "No found")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setDataToTextView()
        setListners()
        setDefaultData()
    }

    private fun setDefaultData() {
        binding.autocompletetxt.setText(
            Constants.getCustomKey(this, Constants.PrefKEY.Topic),
            false
        )
        binding.edtTitle.setText(Constants.getCustomKey(this, Constants.PrefKEY.title))
        binding.edtBody.setText(Constants.getCustomKey(this, Constants.PrefKEY.body))

    }

    private fun setListners() {
        binding.txtLogs.movementMethod = ScrollingMovementMethod()
        binding.btnSendnoti.setOnClickListener {
            val token = activeToken
            val topic = binding.autocompletetxt.text.toString()
            val title = binding.edtTitle.text.toString()
            val body = binding.edtBody.text.toString()
            Constants.setCustomKey(this, Constants.PrefKEY.Topic, topic)
            Constants.setCustomKey(this, Constants.PrefKEY.title, title)
            Constants.setCustomKey(this, Constants.PrefKEY.body, body)

            if (isEmpty(token, topic, title, body).not()) {
                showHideProgress(true)
                viewModel.fcmRetroApiService
                    .sendNotificationToTopic(
                        projectId,
                        token,
                        SendTopicNotificationModel(Topic(Notification(body, title), topic))
                    )
                    .enqueue(object : Callback, retrofit2.Callback<ResponseBody> {
                        override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                        ) {
                            showHideProgress(false)
                            val reposnebody = response.body()?.string() ?: ""

                            appendLog("Code ", response.code().toString())
                            appendLog("raw ", response.raw().toString())
                            appendLog("body ", reposnebody)
                            appendLog("msg ", response.message())
                            if (response.isSuccessful) {
                                showToast("Successfully Send Notification")
                                val newdata = hashset?.toHashSet()?.apply {
                                    add(topic)
                                }
                                Constants.setListOfTopics(this@SendNotifiActivity, newdata)
                                viewModel.insertNotificationsItem(
                                    NotificationRoomDbModel(
                                        topic = topic,
                                        title = title,
                                        body = body,
                                        apiresponse = reposnebody,
                                        responseCode = response.code().toString()
                                    )
                                )
                            } else {
                                if (response.code() == 401) {
                                    showToast("In valid Token Get Refresh Token")
                                    Constants.setToken(this@SendNotifiActivity, null)
                                    binding.btnGettoken.callOnClick()
                                } else {
                                    showToast("Fail Request Code.=" + response.code() + " Msg=" + response.message())
                                }
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            showHideProgress(false)
                            appendLog("onFailure", t.message)
                            showToast("Fail :: " + t.message)
                        }

                    })
            } else {
                showToast("Empty Text")
            }

        }

        binding.btnGettoken.setOnClickListener {
            showHideProgress(true)
            lifecycleScope.launch(Dispatchers.IO) {
                getAccessToken()?.let {
                    showHideProgress(false)
                    activeToken = it
                    appendLog("Token", it)
                } ?: kotlin.run {
                    showHideProgress(false)
                    showToast("Not token found in api or local")
                    activeToken = ""
                    appendLog("Error", "Not Got Token")
                }
            }
        }
        binding.autocompletetxt.setOnTouchListener { view, motionEvent ->
            binding.autocompletetxt.showDropDown()
            false
        }

        binding.ivBody.setOnClickListener {
            val bodylist=viewModel.getListofItems().distinctBy { it.body }.map { SearchListItem(it.hashCode().toInt(),it.body) }
            val searchableDialog = SearchableDialog(this, bodylist, "List Body")
            searchableDialog.setOnItemSelected { i, searchListItem ->
                binding.edtBody.setText(searchListItem.title)
            }
            searchableDialog.show()
        }
        binding.ivTitle.setOnClickListener {
            val bodylist=viewModel.getListofItems().distinctBy { it.title }.map { SearchListItem(it.hashCode().toInt(),it.title) }
            val searchableDialog = SearchableDialog(this, bodylist, "List Title")
            searchableDialog.setOnItemSelected { i, searchListItem ->
                binding.edtTitle.setText(searchListItem.title)
            }
            searchableDialog.show()
        }
    }

    fun setDataToTextView() {
        hashset = Constants.getListOfTopics(this)?.toHashSet() ?: HashSet()
        hashset?.let {
//            val items = it.toList()
            val items = viewModel.getListofItems().distinctBy { it.topic }.map { it.topic }
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
            binding.autocompletetxt.setAdapter(adapter)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        this.openFile()

        if(item.itemId==R.id.menu_main_storage){
            DbInspector.show()
        }else{
            getContent.launch("application/*")
            showToast("SelectServiceAccount.json File ")
        }


        return super.onOptionsItemSelected(item)
    }


    private suspend fun getAccessToken(isFromApi: Boolean = false): String? {

        return if (fileServiveAccount.exists().not()) {
            showToast("ServiceAccount.json Not exist")
            null
        } else if (isFromApi) {
//            val inputStream = resources.openRawResource(R.raw.service_account)
            val googleCredentials = GoogleCredentials
                .fromStream(fileServiveAccount.inputStream())
                .createScoped(listOf(Constants.API_SCOPES))
            googleCredentials.refresh()
            val token = googleCredentials.accessToken.tokenValue
            Constants.setToken(this, token)
            appendLog("token", "FreshToken")
            Constants.getBearerToken(this)
        } else {
            Constants.getBearerToken(this)?.let {
                appendLog("token", "OldToken")
                it
            } ?: kotlin.run {
//                val inputStream = resources.openRawResource(R.raw.service_account)
                try {
                    val googleCredentials = GoogleCredentials
                        .fromStream(fileServiveAccount.inputStream())
                        .createScoped(listOf(Constants.API_SCOPES))
                    googleCredentials.refresh()
                    val token = googleCredentials.accessToken.tokenValue
                    Constants.setToken(this, token)
                    appendLog("token", "FreshToken")
                    Constants.getBearerToken(this)
                } catch (e: Exception) {
                    "${e.message}"
                }
            }
        }
    }


    fun showHideProgress(isVissible: Boolean) {
        runOnUiThread {
            binding.progressDialog.isVisible = isVissible
        }
    }


    fun openDb(view: View) {

    }

    fun isEmpty(vararg title: String): Boolean {
        title.forEach {
            if (it.isNullOrEmpty())
                return true
        }

        return false
    }


    fun appendLog(key: String, value: String?) {
        runOnUiThread {
            binding.txtLogs.append("\n------------------")
            binding.txtLogs.append("-->$key \n -->$value<--")
            binding.txtLogs.append("------------------\n")
        }
    }


}