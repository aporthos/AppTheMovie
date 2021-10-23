package net.portes.appthemovie.ui.images

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import androidx.fragment.app.viewModels
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import dagger.hilt.android.AndroidEntryPoint
import net.portes.appthemovie.R
import net.portes.appthemovie.databinding.FragmentImagesBinding
import net.portes.appthemovie.ui.base.BaseFragment
import net.portes.shared.extensions.getRealPathFromUri
import net.portes.shared.extensions.observe
import net.portes.shared.extensions.toast
import net.portes.shared.ui.base.ViewState

/**
 * @author amadeus.portes
 */
@AndroidEntryPoint
class ImagesFragment : BaseFragment<FragmentImagesBinding>(), EasyPermissions.PermissionCallbacks {

    companion object {
        private const val SELECT_IMAGE = 1
        private const val RC_WRITE_READ_EXTERNAL_STORAGE = 1
    }

    private val viewModel: ImagesViewModel by viewModels()
    private var imageUrl: String? = null

    override fun getLayoutRes(): Int = R.layout.fragment_images

    override fun initializeView() {
        dataBinding().openGalleryButton.setOnClickListener {
            requestPermissions()
        }

        dataBinding().uploadButton.setOnClickListener {
            imageUrl?.let {
                viewModel.loadImage(it)
            } ?: run {
                toast(R.string.message_alert_open_from_gallery)
            }
        }
    }

    override fun initObservers() {
        observe(viewModel.getImageLoad, ::resultLoadImage)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            SettingsDialog.Builder(requireActivity()).build().show()
        } else {
            requestPermissions()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        openGallery()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    private fun requestPermissions() {
        if (hasReadAndWriteExternalStorage()) {
            openGallery()
        } else {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.message_permissions_read_write),
                RC_WRITE_READ_EXTERNAL_STORAGE,
                WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE
            )
        }
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                data?.data?.let {
                    dataBinding().previewImageView.setImageURI(it)
                    imageUrl = requireContext().getRealPathFromUri(it)
                }
            }
        }
    }

    private fun hasReadAndWriteExternalStorage(): Boolean = EasyPermissions.hasPermissions(
        requireContext(),
        READ_EXTERNAL_STORAGE,
        WRITE_EXTERNAL_STORAGE
    )

    private fun resultLoadImage(result: ViewState<Boolean>) {
        when (result) {
            is ViewState.Loading -> showLoader()
            is ViewState.Success -> {
                hideLoader()
                imageUrl = null
                dataBinding().previewImageView.setImageResource(R.drawable.ic_default_gallery)
                toast(R.string.message_upload_image_success)
            }
            is ViewState.Error -> {
                toast(R.string.message_upload_image_failed)
                hideLoader()
            }
        }
    }
}