package com.rediz.pokemon.presentation.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.rediz.pokemon.common.collectEvent

open class BaseActivity : AppCompatActivity() {
    protected val mContext: Context by lazy { this }
    var baseViewModelLazy: Lazy<BaseViewModel>? = null

    // ActivityResult
    private var onResult: (Intent?) -> Unit = {}

    //ActivityResultLauncher
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) onResult(it.data)
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        baseViewModelLazy?.value?.let {
            collectEvent(it.commonEvent, ::handleEvent)
        }
    }

    private fun handleEvent(event: BaseViewModel.CommonEvent) {

    }

    override fun onStart() {
        super.onStart()
        // Activity 전환 애니메이션 삭제
        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, 0, 0)
        } else {
            @Suppress("DEPRECATION")
            overridePendingTransition(0, 0)
        }
    }

    /**
     * @param intent   실행 인텐트
     * @param onResult Callback 메서드
     *
     * StartActivityForResult
     */
    fun startActivityForResult(intent: Intent, onResult: (Intent?) -> Unit) {
        this.onResult = onResult
        resultLauncher.launch(intent)
    }
}

/**
 * BaseViewModel delegate
 */
@MainThread
inline fun <reified VM : BaseViewModel> BaseActivity.viewModel(
    noinline extrasProducer: (() -> CreationExtras)? = null,
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null,
): Lazy<VM> {
    val factoryPromise = factoryProducer ?: {
        defaultViewModelProviderFactory
    }

    val lazyViewModel = ViewModelLazy(
        VM::class,
        { viewModelStore },
        factoryPromise,
        { extrasProducer?.invoke() ?: this.defaultViewModelCreationExtras }
    )

    baseViewModelLazy = lazyViewModel
    return lazyViewModel
}