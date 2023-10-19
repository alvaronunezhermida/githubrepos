package com.githubrepos.app.screens.error

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.githubrepos.app.R
import com.githubrepos.app.databinding.FragmentErrorBinding
import com.githubrepos.app.mappers.toDomain
import com.githubrepos.app.screens.BaseDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ErrorFragment : BaseDialog<FragmentErrorBinding, ErrorViewModel>() {

    override val viewModel: ErrorViewModel by viewModels()
    private val entry: ErrorEntry
        get() = arguments?.getParcelable(ErrorArgs.ENTRY)
            ?: ErrorEntry.Unknown

    private var listener: ErrorListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = (context as? AppCompatActivity) as? ErrorListener
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentErrorBinding = FragmentErrorBinding.inflate(inflater, container, false)

    override fun initViews() {
        super.initViews()
        isCancelable = false

        initDetailText()
        initOkButton()
    }

    private fun initDetailText() {
        binding.detailText.text = when (entry) {
            is ErrorEntry.Custom -> (entry as ErrorEntry.Custom).run {
                detail.ifEmpty {
                    title
                }
            }

            ErrorEntry.NullParams,
            ErrorEntry.Unknown -> getString(R.string.ok)
        }
    }

    private fun initOkButton() {
        binding.okButton.setOnClickListener {
            viewModel.onOkButtonClicked()
            listener?.onErrorOkClicked(entry.toDomain())
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}