package khani.behnam.veroapplication

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import khani.behnam.common.shared.QRCodeScanResultCallback
import khani.behnam.common.shared.SharedViewModel
import khani.behnam.qrcodescanner.QRCodeScanner
import khani.behnam.veroapplication.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), QRCodeScanResultCallback {

    private lateinit var qrCodeScanner: QRCodeScanner
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var binding: ActivityMainBinding
    private var searchView: SearchView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.toolbar)
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]
        qrCodeScanner = QRCodeScanner(this, this)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (searchView != null) return true
        menuInflater.inflate(R.menu.main_menu, menu)

        searchView = menu?.findItem(R.id.action_search)?.actionView as? SearchView
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                sharedViewModel.setText(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                    if (newText.isEmpty()){
                        sharedViewModel.setText("")
                    }else{
                        sharedViewModel.setText(newText)
                    }
                return true
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_scan -> {
                qrCodeScanner.initiateScan()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        qrCodeScanner.handleActivityResult(requestCode, resultCode, data)
    }

    override fun onQRCodeScanResult(result: String?) {
        if (result != null) {
            if (result.isNotEmpty()){
                searchView?.isIconified = false
                searchView?.setQuery(result, false)
            }
        }

    }

}