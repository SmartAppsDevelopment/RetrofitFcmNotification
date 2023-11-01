package com.example.templatesampleapp.ui.activmain

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.templatesampleapp.R
import com.example.templatesampleapp.base.BaseActivity
import com.example.templatesampleapp.databinding.ActivityMainBinding
import com.example.templatesampleapp.helper.Constants
import com.google.auth.oauth2.GoogleCredentials
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Arrays


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding.rvItemList.adapter = SampleAdapter().apply {
//            submitList(viewModel.getData().value)
//        }
//        viewModel.sendNotifiation()
        binding.txtLogs.movementMethod = ScrollingMovementMethod()
        lifecycleScope.launch(Dispatchers.IO) {
            Constants.getToken(this@MainActivity)?.let {
                appendLog("OldToken", it)
                Log.e("MainActivity", "onCreate: AccessToken--" + it)
            } ?: kotlin.run {
                val token = getAccessToken()
                Constants.setToken(this@MainActivity, token)
                Log.e("MainActivity", "onCreate: AccessToken--" + token)
                appendLog("NewToken", token ?: "")
            }

        }
        setListners()
    }


    fun setListners() {
        binding.btnSendnotif.setOnClickListener {
         viewModel.sendNotifiation(this,this)
        }
    }


    private fun getAccessToken(): String? {

//        val options = FirebaseOptions.builder()
//            .setCredentials(GoogleCredentials.getApplicationDefault())
//            .setProjectId("sampleprojectstest-b9d22")
//            .build()


        val inputStream = resources.openRawResource(R.raw.service_account)


        val googleCredentials = GoogleCredentials
            .fromStream(inputStream)
            .createScoped(Arrays.asList<String>(Constants.API_SCOPES))
        googleCredentials.refresh()
        return googleCredentials.accessToken.tokenValue
    }


    fun appendLog(key: String, value: String?) {
        runOnUiThread {
            binding.txtLogs.append("\n------------------")
            binding.txtLogs.append("-->$key \n -->$value<--")
            binding.txtLogs.append("------------------\n")
        }
    }
}