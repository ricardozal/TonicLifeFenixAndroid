package com.bigtechsolutions.toniclifefenix.ui.profile.register_points

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bigtechsolutions.toniclifefenix.R
import com.bigtechsolutions.toniclifefenix.api.requests.DistributorPointsRequest
import com.bigtechsolutions.toniclifefenix.api.requests.GetCandidatesRequest
import com.bigtechsolutions.toniclifefenix.api.requests.ProductRequest
import com.bigtechsolutions.toniclifefenix.api.requests.SaveOrderWithDistRequest
import com.bigtechsolutions.toniclifefenix.api.responses.share_points.SharePointsResponse
import com.bigtechsolutions.toniclifefenix.commons.Constants
import com.bigtechsolutions.toniclifefenix.commons.MyFenixApp
import com.bigtechsolutions.toniclifefenix.commons.SharedPreferencesManager
import com.bigtechsolutions.toniclifefenix.data.entity.ShoppingCart
import com.bigtechsolutions.toniclifefenix.databinding.ActivityRegisterPointsBinding
import com.bigtechsolutions.toniclifefenix.databinding.ItemDistributorPointsBinding
import com.bigtechsolutions.toniclifefenix.ui.NewDistributorActivity
import com.bigtechsolutions.toniclifefenix.ui.shoppingcart.ShoppingCartActivity
import com.bigtechsolutions.toniclifefenix.viewmodel.DistributorViewModel
import com.bigtechsolutions.toniclifefenix.viewmodel.OrderViewModel
import com.bigtechsolutions.toniclifefenix.viewmodel.ShoppingCartViewModel
import com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnOrderResponse
import com.bigtechsolutions.toniclifefenix.viewmodel.interfaces.OnSharePointsResponse

class RegisterPointsActivity : AppCompatActivity() {

    lateinit var vBind: ActivityRegisterPointsBinding
    var loading: ProgressDialog? = null

    var modiPoints : Double = 0.0
    var pointsForDistributor : Double = 0.0;

    private var productsRequest: MutableList<ProductRequest> = ArrayList()
    private var productViewModel: ShoppingCartViewModel? = null
    private var distributorViewModel: DistributorViewModel? = null
    private var orderViewModel: OrderViewModel? = null

    val list: MutableList<asignedPoints> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vBind = DataBindingUtil.setContentView(this, R.layout.activity_register_points)

        val bundle :Bundle ?=intent.extras
        val addressId = bundle!!.getInt("addressId")

        toolbarConfig(vBind.toolbar)

        distributorViewModel = ViewModelProvider(this)
                .get(DistributorViewModel::class.java)

        orderViewModel = ViewModelProvider(this)
                .get(OrderViewModel::class.java)

        productViewModel = ViewModelProvider(this)
                .get(ShoppingCartViewModel::class.java)

        getShoppingCart()

        vBind.btnAssign.setOnClickListener {

            if(list.isNotEmpty()) {
                loading = ProgressDialog.show(this, "Cargando", "Por favor espere...", false, false)

                var request = SaveOrderWithDistRequest()
                val listDist: MutableList<DistributorPointsRequest> = ArrayList()

                for (item in list){
                    val dist = DistributorPointsRequest()
                    dist.id = item.tonicLifeId
                    dist.points = item.points.toDouble()
                    listDist.add(dist)
                }

                val distributorId = SharedPreferencesManager.getIntValue(Constants.DISTRIBUTOR_ID)
                val branchId = SharedPreferencesManager.getIntValue(Constants.BRANCH_ID)

                request.distributors = listDist
                request.distributorId = distributorId
                request.branchId = branchId
                request.products = productsRequest
                request.addressId = addressId
                request.pointsForDist= pointsForDistributor
                request.leftover= modiPoints

                orderViewModel!!.saveOrderWithExternalPoints(request, object : OnOrderResponse{
                    override fun OnSuccess(title: String?, message: String?, orderId: Int?, currentPoints: Double?) {

                        val kitsNumber = productViewModel!!.numberKits
                        productViewModel!!.deleteAll()
                        SharedPreferencesManager.removeValue(Constants.BRANCH_ID)
                        SharedPreferencesManager.removeValue(Constants.CURRENT_POINTS)
                        SharedPreferencesManager.setStringValue(Constants.CURRENT_POINTS, currentPoints.toString())

                        loading!!.dismiss()

                        displayAlertOrder(
                                title!!,
                                message,
                                kitsNumber > 0,
                                orderId!!
                        )

                    }

                    override fun OnError(title: String?, message: String?) {

                        loading!!.dismiss()

                        displayAlertOrder(
                                title!!,
                                message,
                                false,
                                0
                        )

                    }


                })


            } else {
                displayAlert("Atención", "Primero debes ingresar los puntos correspondientes", false)
            }

        }

    }

    private fun getShoppingCart() {
        productViewModel!!.all.observe(this, object : Observer<List<ShoppingCart>> {
            override fun onChanged(shoppingCarts: List<ShoppingCart>) {
                for (product in shoppingCarts) {
                    productsRequest.add(ProductRequest(product.getProductId(), product.getQuantity()))
                }
                productViewModel!!.all.removeObserver(this)
                getDistributors()
            }
        })
    }

    private fun getDistributors() {

        loading = ProgressDialog.show(this, "Cargando", "Por favor espere...", false, false)

        val distributorId = SharedPreferencesManager.getIntValue(Constants.DISTRIBUTOR_ID)

        var request = GetCandidatesRequest()

        request.distributorId = distributorId
        request.products = this.productsRequest

        distributorViewModel!!.getCandidates(request, object : OnSharePointsResponse{
            @SuppressLint("SetTextI18n")
            override fun OnSuccess(sharePointsResponse: SharePointsResponse?) {

                modiPoints = sharePointsResponse!!.totalPoints

                pointsForDistributor = sharePointsResponse.pointsForDistributor
                vBind.instructions.text = sharePointsResponse.toDist
                vBind.txOriginalPoints.text = "Puntos de orden: "+sharePointsResponse.originalPoints
                vBind.txCurrentPoints.text = "Puntos sobrantes: $modiPoints"

                if(sharePointsResponse.candidates != null){
                    sharePointsResponse.candidates.forEach { candidate ->

                        val v = layoutInflater.inflate(R.layout.item_distributor_points, vBind.llDistributors, false)
                        val externalBinding = ItemDistributorPointsBinding.bind(v)

                        externalBinding.distName.text = candidate.name
                        externalBinding.pointsTonic.text = "Le faltan para calificar: "+candidate.difference
                        externalBinding.itemRoot.setBackgroundColor(Color.parseColor(candidate.color))


                        externalBinding.swActive.setOnCheckedChangeListener { _, isChecked ->
                            externalBinding.tlPoints.error = null
                            if (isChecked){
                                val pattern = Regex("^[0-9]{1,4}$")
                                if (externalBinding.etPoints.text.toString().trim().isEmpty()) {
                                    externalBinding.tlPoints.error = getString(R.string.error_points)
                                    externalBinding.swActive.isChecked = false
                                } else if(pattern.containsMatchIn(externalBinding.etPoints.text.toString())) {

                                    if(((modiPoints - externalBinding.etPoints.text.toString().toInt()) >= 0)){

                                        modiPoints -= externalBinding.etPoints.text.toString().toInt()
                                        v.tag = candidate.id.toString()
                                        var dist = asignedPoints()
                                        dist.tonicLifeId = candidate.id.toString()
                                        dist.points = externalBinding.etPoints.text.toString().toInt()
                                        list.add(dist)

                                        vBind.txCurrentPoints.text = "Puntos sobrantes: $modiPoints"

                                        externalBinding.etPoints.isEnabled = false
                                        externalBinding.etPoints.isFocusable = false

                                    } else{
                                        externalBinding.swActive.isChecked = false
                                        displayAlert("Atención", "Verifica tus datos", false)
                                    }

                                } else {
                                    externalBinding.swActive.isChecked = false
                                    displayAlert("Atención", "Verifica tus datos", false)
                                }
                            } else {

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

                                vBind.txCurrentPoints.text = "Puntos: $modiPoints"

                                externalBinding.etPoints.isEnabled = true
                                externalBinding.etPoints.isFocusable = true

                            }
                        }

                        vBind.llDistributors.addView(v)

                    }
                }

                if(sharePointsResponse.candidatesPromos != null){
                    sharePointsResponse.candidatesPromos.forEach { candidate ->

                        val v = layoutInflater.inflate(R.layout.item_distributor_points, vBind.llDistributors, false)
                        val externalBinding = ItemDistributorPointsBinding.bind(v)

                        externalBinding.distName.text = candidate.name
                        externalBinding.pointsTonic.text = "Le faltan para alcanzar promoción: "+candidate.getPoints
                        externalBinding.itemRoot.setBackgroundColor(Color.parseColor(candidate.color))


                        externalBinding.swActive.setOnCheckedChangeListener { _, isChecked ->
                            externalBinding.tlPoints.error = null
                            if (isChecked){
                                val pattern = Regex("^[0-9]{1,4}$")
                                if (externalBinding.etPoints.text.toString().trim().isEmpty()) {
                                    externalBinding.tlPoints.error = getString(R.string.error_points)
                                    externalBinding.swActive.isChecked = false
                                } else if(pattern.containsMatchIn(externalBinding.etPoints.text.toString())) {

                                    if(((modiPoints - externalBinding.etPoints.text.toString().toInt()) >= 0)){

                                        modiPoints -= externalBinding.etPoints.text.toString().toInt()
                                        v.tag = candidate.id.toString()
                                        var dist = asignedPoints()
                                        dist.tonicLifeId = candidate.id.toString()
                                        dist.points = externalBinding.etPoints.text.toString().toInt()
                                        list.add(dist)

                                        vBind.txCurrentPoints.text = "Puntos sobrantes: $modiPoints"

                                        externalBinding.etPoints.isEnabled = false
                                        externalBinding.etPoints.isFocusable = false

                                    } else{
                                        externalBinding.swActive.isChecked = false
                                        displayAlert("Atención", "Verifica tus datos", false)
                                    }

                                } else {
                                    externalBinding.swActive.isChecked = false
                                    displayAlert("Atención", "Verifica tus datos", false)
                                }
                            } else {

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

                                vBind.txCurrentPoints.text = "Puntos: $modiPoints"

                                externalBinding.etPoints.isEnabled = true
                                externalBinding.etPoints.isFocusable = true

                            }
                        }

                        vBind.llDistributors.addView(v)

                    }
                    loading!!.dismiss()
                }

                if(sharePointsResponse.candidates == null && sharePointsResponse.candidatesPromos == null) {
                    loading!!.dismiss()
                    displayAlert(
                            "Atención",
                            "Primero debe calificar, vaya a la opción de conservar puntos",
                            true
                    )
                }else{
                    loading!!.dismiss()
                }

            }

            override fun OnError(title: String?, message: String?) {
                loading!!.dismiss()
                displayAlert(
                        title!!,
                        message,
                        false
                )
            }

        })

    }

    private fun toolbarConfig(toolbar: Toolbar) {

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_action_back)
        toolbar.setNavigationOnClickListener {

            finish()

        }
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

    private fun displayAlertOrder(title: String,
                             message: String?,
                             isKit: Boolean,
                             orderId: Int) {
        val builder = AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
        builder.setPositiveButton("Ok") { dialog, which ->
            if (isKit) {
                val i = Intent(MyFenixApp.getContext(), NewDistributorActivity::class.java)
                val bundle = Bundle()
                bundle.putInt("orderId", orderId)
                i.putExtras(bundle)
                startActivity(i)
                finish()
            } else {
                val i = Intent(MyFenixApp.getContext(), ShoppingCartActivity::class.java)
                startActivity(i)
                finish()
            }
        }
        builder.setCancelable(false)
        builder.create().show()
    }
}