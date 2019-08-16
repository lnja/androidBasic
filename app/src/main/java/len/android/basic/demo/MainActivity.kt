package len.android.basic.demo

import android.os.Bundle
import android.view.View
import android.widget.TextView
import len.android.basic.activity.BaseActivity
import len.android.basic.dialog.AlertDialog
import len.android.basic.dialog.BottomDialog
import len.android.basic.dialog.PickPhotoDialog
import len.tools.android.Log

class MainActivity : BaseActivity(){

    private lateinit var textView:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onInit() {
        super.onInit()
    }


    override fun onFindViews() {
        super.onFindViews()
        textView = findViewById<TextView>(R.id.tv_tip)
    }

    override fun onBindListener() {
        super.onBindListener()
        textView.setOnClickListener(this)
    }

    override fun onFillDataToViews() {
        super.onFillDataToViews()
    }

    override fun onClick(view: View?) {
        super.onClick(view)
        if(view!!.id == R.id.tv_tip){
            Log.e("tip view clicked")
            showDialog()
        }
    }

    private fun showDialog(){
//        var dialog = AlertDialog(this,"示例","I am dialog demo")
        var dialog = BottomDialog(this)
        dialog.contentLayout.addView(layoutInflater.inflate(R.layout.dialog_pick_time,null))
        dialog.show()

       /* var dialog = PickPhotoDialog(this,"pick photo dialog demo")
        dialog.setFront(false)
        dialog.setUseCustomCamera(true)
        dialog.show(100)*/
    }
}
