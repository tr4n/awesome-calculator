package com.tr4n.awesomecalculator

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.tr4n.awesomecalculator.databinding.ActivityMainBinding
import com.tr4n.awesomecalculator.listener.OnSimpleItemClick

class MainActivity : AppCompatActivity(), OnSimpleItemClick<Item> {

    private val viewBD by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewBD.apply {
            lifecycleOwner = this@MainActivity
            viewModel = this@MainActivity.viewModel
            listener = this@MainActivity
            executePendingBindings()
        }
    }

    override fun onClick(item: Item) {
        when {
            item.type.isOperatorOrOperand() -> {
                viewModel.append(item)
            }
            item.type == ButtonType.CLEAR -> {
                viewModel.clear()
            }
            item.type == ButtonType.BACK -> {
                viewModel.delete()
            }
            item.type == ButtonType.EQUALS -> {
                viewModel.calculate()
            }
        }
    }
}
