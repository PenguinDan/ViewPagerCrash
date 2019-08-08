package com.test.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.test.myapplication.databinding.ActivityMainBinding
import java.lang.IllegalArgumentException

class MainActivity : AppCompatActivity() {
    private var currFragmentId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.button1.setOnClickListener { openFragment(1) }
        binding.button2.setOnClickListener { openFragment(2) }

        // Default open Second fragment
        openFragment(2)
    }

    private fun openFragment(fragmentToOpen: Int) {
        if(currFragmentId == fragmentToOpen) return
        currFragmentId = fragmentToOpen

        val transaction = supportFragmentManager.beginTransaction()

        var fragment: Fragment? = supportFragmentManager.findFragmentByTag("Frag$fragmentToOpen")

        if(fragment == null) {
            fragment = when(fragmentToOpen) {
                1 -> ViewPagerFragment()
                2 -> BlankFragment()
                else -> throw IllegalArgumentException()
            }
        }
        transaction.replace(R.id.mainFragmentFrame, fragment, "Frag$fragmentToOpen")
        transaction.addToBackStack(null) // Comment this line to stop the error
        transaction.commit()
    }
}
