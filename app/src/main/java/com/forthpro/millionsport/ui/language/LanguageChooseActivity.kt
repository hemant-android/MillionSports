package com.forthpro.millionsport.ui.language

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.forthpro.millionsport.BaseActivity
import com.forthpro.millionsport.databinding.ActivityLanguageChooseBinding
import com.forthpro.millionsport.ui.language.adapter.LanguageChooseAdapter
import com.forthpro.millionsport.ui.timing.ChooseTimeFormatActivity


class LanguageChooseActivity : BaseActivity(), LanguageChooseAdapter.onClickListner {

    private lateinit var binding: ActivityLanguageChooseBinding
    private val adapter: LanguageChooseAdapter by lazy { LanguageChooseAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageChooseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvChooseLanguage.adapter = adapter
        adapter.setClickListner(this)
    }

    override fun clickItem(itemPosition: String) {
//        Toast.makeText(applicationContext,"Selected position: $itemPosition", Toast.LENGTH_SHORT).show()

        Intent(this, ChooseTimeFormatActivity::class.java).also {
            startActivity(it)
        }
    }
}