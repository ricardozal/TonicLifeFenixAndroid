package com.bigtechsolutions.toniclifefenix.ui.profile.register_points

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.bigtechsolutions.toniclifefenix.R
import com.bigtechsolutions.toniclifefenix.commons.MyFenixApp
import com.bigtechsolutions.toniclifefenix.databinding.ActivityRegisterPointsBinding
import com.bigtechsolutions.toniclifefenix.ui.profile.OrderShowActivity

class RegisterPointsActivity : AppCompatActivity() {

    lateinit var vBind: ActivityRegisterPointsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vBind = DataBindingUtil.setContentView(this, R.layout.activity_register_points)

        val bundle :Bundle ?=intent.extras
        val orderId = bundle!!.getInt("orderId")

        toolbarConfig(vBind.toolbar, orderId)

    }

    private fun toolbarConfig(toolbar: Toolbar, orderId: Int) {

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_action_back)
        toolbar.setNavigationOnClickListener {

            val i = Intent(MyFenixApp.getContext(), OrderShowActivity::class.java)
            i.putExtra("orderId", orderId)
            startActivity(i)
            finish()

        }
    }
}