package com.example.kotlinfragment.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.kotlinfragment.R
import com.example.kotlinfragment.ResultActivity
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.toast

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater?.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_sum.setOnClickListener{
            val a: String = edit_a.text.toString()
            val b: String = edit_b.text.toString()

            startActivity(intentFor<ResultActivity>("a" to a, "b" to b))
        }
    }

}
