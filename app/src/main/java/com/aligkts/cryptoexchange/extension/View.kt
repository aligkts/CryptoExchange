package com.aligkts.cryptoexchange.extension

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Created by Ali Göktaş on 11.08.2020
 */
fun <T : ViewDataBinding> ViewGroup?.inflate(@LayoutRes layoutId: Int, attachToParent: Boolean = true): T {
    return DataBindingUtil.inflate(LayoutInflater.from(this!!.context), layoutId, this, attachToParent)
}

fun View.playAnimation(@AnimRes animResId: Int,
                       durationTime: Long) = with(AnimationUtils.loadAnimation(context, animResId)) {
    duration = durationTime
    startAnimation(this)
}