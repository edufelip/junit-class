package com.example.junitclass.ui.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.junitclass.R
import com.example.junitclass.ui.interfaces.OnBackPressed
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var fragmentFactory: MainFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.fragmentFactory = fragmentFactory
        supportFragmentManager.beginTransaction()
            .commit()
    }

    override fun onBackPressed() {
        tellFragments()
        super.onBackPressed()
    }

    private fun tellFragments() {
        val fragments: List<Fragment> = supportFragmentManager.fragments
        for (fragment in fragments) {
            if (fragment is AddItemFragment) (fragment as AddItemFragment).onBackPressed()
        }
    }
}