package com.bigtechsolutions.toniclifefenix.ui.profile.register_points

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bigtechsolutions.toniclifefenix.R
import com.bigtechsolutions.toniclifefenix.api.requests.DistributorPointsRequest
import com.bigtechsolutions.toniclifefenix.api.requests.RegisterPointsRequest
import com.bigtechsolutions.toniclifefenix.databinding.ActivityRegisterPointsBinding
import com.bigtechsolutions.toniclifefenix.databinding.ItemDistributorPointsBinding
import com.bigtechsolutions.toniclifefenix.viewmodel.DistributorViewModel
import com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnResponse

class RegisterPointsActivity : AppCompatActivity() {

    lateinit var vBind: ActivityRegisterPointsBinding
    var loading: ProgressDialog? = null

    var modiPoints : Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vBind = DataBindingUtil.setContentView(this, R.layout.activity_register_points)

        val bundle :Bundle ?=intent.extras
        val orderId = bundle!!.getInt("orderId")
        val price = bundle!!.getDouble("price")
        val points = bundle!!.getDouble("points")
        val countryId = bundle!!.getInt("countryId")

        toolbarConfig(vBind.toolbar, orderId)

        val distributorViewModel = ViewModelProvider(this)
                .get(DistributorViewModel::class.java)

        /**
         * countryId = 1 : Mexico
         * countryId = 2 : USA
         */
        modiPoints = if (countryId == 1) points else price;

        val list: MutableList<asignedPoints> = ArrayList()

        vBind.txCurrentPoints.text = "Puntos: $modiPoints"

        vBind.btnSelect.setOnClickListener {

            if(validate()){

                val v = layoutInflater.inflate(R.layout.item_distributor_points, vBind.llDistributors, false)
                val externalBinding = ItemDistributorPointsBinding.bind(v)
                var exist : Boolean = false

                for (item in list){
                    exist = item.tonicLifeId == vBind.etTonicLifeFenix.text.toString()
                }

                val pattern = Regex("^[0-9]{1,4}$")

                if(pattern.containsMatchIn(vBind.etPoints.text.toString())){
                    Log.i("DESPUES DE REG", "SI")
                    if(((modiPoints - vBind.etPoints.text.toString().toInt()) >= 0) && !exist){

                        val pointsStr: String = vBind.etPoints.text.toString() + " puntos"

                        externalBinding.tonicId.text = vBind.etTonicLifeFenix.text.toString()
                        externalBinding.pointsTonic.text = pointsStr

                        externalBinding.btnDelete.setOnClickListener {

                            val tag : String = externalBinding.itemRoot.tag as String

                            if(list.isNotEmpty()) {
                                val current = list.filter { it.tonicLifeId == tag }

                                modiPoints += current.first().points

                                val listTem = list.filter { it.tonicLifeId != tag }

                                list.clear()

                                for (item in listTem){
                                    list.add(item)
                                }
                            }

                            externalBinding.itemRoot.removeViewAt(0)
                            Log.i("Points", modiPoints.toString())
                            vBind.txCurrentPoints.text = "Puntos: $modiPoints"
                        }

                        modiPoints -= vBind.etPoints.text.toString().toInt()
                        v.tag = vBind.etTonicLifeFenix.text.toString()
                        var dist = asignedPoints()
                        dist.tonicLifeId = vBind.etTonicLifeFenix.text.toString()
                        dist.points = vBind.etPoints.text.toString().toInt()
                        list.add(dist)
                        vBind.etTonicLifeFenix.text?.clear()
                        vBind.etPoints.text?.clear()
                        vBind.llDistributors.addView(v)

                        Log.i("Points", modiPoints.toString())
                        vBind.txCurrentPoints.text = "Puntos: $modiPoints"
                    } else {
                        displayAlert("Atención", "Verifica tus datos", false)

                    }
                } else {
                    displayAlert("Atención", "Verifica tus datos", false)
                }

            }

        }

        vBind.btnAssign.setOnClickListener {

            if(list.isNotEmpty()) {
                loading = ProgressDialog.show(this, "Cargando", "Por favor espere...", false, false)

                var request = RegisterPointsRequest()
                val listDist: MutableList<DistributorPointsRequest> = ArrayList()

                for (item in list){
                    val dist = DistributorPointsRequest()
                    dist.id = item.tonicLifeId
                    dist.points = item.points.toDouble()
                    listDist.add(dist)
                }

                request.orderId = orderId
                request.distributors = listDist

                distributorViewModel.registerPoints(request, object : OnResponse {
                    override fun OnSuccess(title: String, message: String) {
                        loading!!.dismiss()
                        displayAlert("Éxito", message, true)
                    }
                    override fun OnError(title: String, message: String) {
                        loading!!.dismiss()
                        displayAlert(title, message, false)
                    }
                })

            } else {
                displayAlert("Atención", "Primero debes ingresar a los distribuidores", false)
            }

        }

    }

    private fun toolbarConfig(toolbar: Toolbar, orderId: Int) {

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_action_back)
        toolbar.setNavigationOnClickListener {

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

    private fun displayAlert(
            title: String,
            message: String?,
            success: Boolean
    ) {
        val builder = AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
        builder.setPositiveButton("Ok") { dialog, which ->
            if(success){
                finish()
            } else {
                dialog.dismiss()
            }
        }
        builder.setCancelable(false)
        builder.create().show()
    }
}