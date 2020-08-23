package com.bigtechsolutions.toniclifefenix.ui.profile.register_points

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.bigtechsolutions.toniclifefenix.R
import com.bigtechsolutions.toniclifefenix.commons.Constants
import com.bigtechsolutions.toniclifefenix.commons.MyFenixApp
import com.bigtechsolutions.toniclifefenix.commons.SharedPreferencesManager
import com.bigtechsolutions.toniclifefenix.databinding.ActivityRegisterPointsBinding
import com.bigtechsolutions.toniclifefenix.databinding.ItemDistributorPointsBinding
import com.bigtechsolutions.toniclifefenix.ui.profile.OrderShowActivity

class RegisterPointsActivity : AppCompatActivity() {

    lateinit var vBind: ActivityRegisterPointsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vBind = DataBindingUtil.setContentView(this, R.layout.activity_register_points)

        val bundle :Bundle ?=intent.extras
        val orderId = bundle!!.getInt("orderId")
        val price = bundle!!.getInt("price")
        val points = bundle!!.getInt("points")

        toolbarConfig(vBind.toolbar, orderId)

//        var modiPoints : Int = SharedPreferencesManager.getIntValue(Constants.C)
//
//        vBind.txCurrentPoints.text = "Puntos: " +

        vBind.btnSelect.setOnClickListener {

            if(validate()){

                val v = layoutInflater.inflate(R.layout.item_distributor_points, vBind.llDistributors, false)
                val externalBinding = ItemDistributorPointsBinding.bind(v)

                val pointsStr: String = vBind.etPoints.text.toString() + " puntos"

                externalBinding.tonicId.text = vBind.etTonicLifeFenix.text.toString()
                externalBinding.pointsTonic.text = pointsStr

                externalBinding.btnDelete.setOnClickListener {

                    externalBinding.itemRoot.removeViewAt(0)

                }

                vBind.llDistributors.addView(v)

                vBind.etTonicLifeFenix.text?.clear()
                vBind.etPoints.text?.clear()

            }

        }

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

    private fun validate(): Boolean {
        var formOk = true
        clearErrors()

        if (vBind.etTonicLifeFenix.text.toString().trim().isEmpty()) {
            formOk = false
            vBind.tlTonicLifeId.error = getString(R.string.error_tonic_life_id)
        }
        if (vBind.etPoints.text.toString().trim().isEmpty()) {
            formOk = false
            vBind.tlPoints.error = getString(R.string.error_points)
        }
        return formOk
    }

    private fun clearErrors() {
        vBind.tlTonicLifeId.error = null
        vBind.tlPoints.error = null
    }
}