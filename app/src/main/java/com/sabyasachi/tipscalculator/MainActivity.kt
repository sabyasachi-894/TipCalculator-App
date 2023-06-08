package com.sabyasachi.tipscalculator

import androidx.appcompat.app.AppCompatActivity
import android.widget.SeekBar
import android.widget.TextView
import android.widget.EditText
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.SeekBar.OnSeekBarChangeListener

private const val TAG="MainActivity"
private const val INITIAL_TIP_PERCENT=15

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val seekBarTip: SeekBar = findViewById(R.id.seekBarTip)
        val tvTipPercentage: TextView=findViewById(R.id.tvTipPercentage)
        seekBarTip.progress=15
        tvTipPercentage.text="$INITIAL_TIP_PERCENT%"
        updateTipDescription(INITIAL_TIP_PERCENT)
        seekBarTip.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG,"onProgressChange $progress")
                //val tvTipPercentage: TextView=findViewById(R.id.tvTipPercentage)
                tvTipPercentage.text= "$progress%"
                computeTipAndTotal()
                updateTipDescription(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        val etBase: EditText=findViewById(R.id.etBase)
        etBase.addTextChangedListener(object : TextWatcher{
            override fun equals(other: Any?): Boolean {
                return super.equals(other)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG,"afterTextChanged $s")
                computeTipAndTotal()
            }
        })

    }

    private fun computeTipAndTotal(){
        val etBase: EditText=findViewById(R.id.etBase)
        val seekBarTip: SeekBar = findViewById(R.id.seekBarTip)
        val tvTipAmount: TextView=findViewById(R.id.tvTipAmount)
        val tvTotalAmount: TextView=findViewById(R.id.tvTotalAmount)

        if(etBase.text.isEmpty())
        {
            tvTipAmount.text=""
            tvTotalAmount.text=""
            return
        }

        val baseAmount=etBase.text.toString().toDouble()
        val tipPercent=seekBarTip.progress.toDouble()
        val totalTip=baseAmount*tipPercent/100
        val totalAmount=baseAmount+totalTip

        //tvTipAmount.text=totalTip.toString()
        tvTipAmount.text="%.2f".format(totalTip)
        tvTotalAmount.text="%.2f".format(totalAmount)

    }

    private fun updateTipDescription(tipPercent : Int){
        val tvTipDescription: TextView=findViewById(R.id.tvTipDescription)
        var tipDescription : String=""
        when(tipPercent){
            in 0..9-> tipDescription="Poor"
            in 10..14-> tipDescription="Acceptable"
            in 15..19-> tipDescription="Good"
            in 20..24-> tipDescription="Great"
            else->"Amazing"
        }
        tvTipDescription.text=tipDescription
    }

}