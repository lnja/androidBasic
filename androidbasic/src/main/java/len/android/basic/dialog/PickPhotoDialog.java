package len.android.basic.dialog;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.LinearLayout;
import len.android.basic.R;
import len.android.basic.activity.BaseActivity;
import len.android.basic.activity.CameraForIdCardActivity;
import len.android.basic.config.Config;
import len.tools.android.*;

import java.io.File;
import java.util.UUID;

public class PickPhotoDialog extends Dialog {

    private BaseActivity context;
    private File photoFile;
    private int requestCode;
    private String title;
    private boolean isUseCustomCamera = false;
    private boolean isFront = false;

    private LinearLayout tvCamare, tvLocal;

    public PickPhotoDialog(BaseActivity context, int requestCode, String title) {
        super(context);
        this.context = context;
        this.requestCode = requestCode;
        this.title = title;
        init();
    }

    public PickPhotoDialog(BaseActivity context, String title) {
        super(context);
        this.context = context;
        this.title = title;
        init();
    }

    public void setFront(boolean isFront) {
        this.isFront = isFront;
    }

    public void setUseCustomCamera(boolean isUseCustomCamera) {
        this.isUseCustomCamera = isUseCustomCamera;
    }

    private void init() {
        photoFile = StorageUtils.getCacheCustomDir(context, "localImg");
        photoFile = new File(photoFile, UUID.randomUUID().toString() + ".jpg");
        setTitle(title);
        setContentView(R.layout.dialog_pick_photo);

        tvCamare = (LinearLayout) findViewById(R.id.ll_camare);
        tvLocal = (LinearLayout) findViewById(R.id.ll_local);

        tvCamare.setOnClickListener(this);
        tvLocal.setOnClickListener(this);
        fixPicPhotoError();
    }

    private void fixPicPhotoError() {
        // android 7.0系统解决拍照的问题android.os.FileUriExposedException: exposed beyond app through ClipData.Item.getUri()
        if (Build.VERSION.SDK_INT >= 18) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }
    }

    public void show(int requestCode) {
        this.requestCode = requestCode;
        super.show();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.ll_camare) {
            execute(1);
        } else if (view.getId() == R.id.ll_local) {
            execute(0);
        }
    }

    @Override
    public void onConfirm() {
        execute(1);
    }

    @Override
    public void onCancel() {
        execute(0);
    }


    private void execute(int operate) {
        photoFile.delete();

        Intent intent;
        if (operate == 0) {// from gallery
            intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        } else {// from camera
            if (isUseCustomCamera) {
                intent = new Intent(context, CameraForIdCardActivity.class);
                intent.putExtra("isFront", isFront);
            } else {
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
            }
        }
        try {
            context.startActivityForResult(intent, requestCode);
        } catch (ActivityNotFoundException e) {
            Log.e(e.getMessage());
            context.getToastDialog().showToast(
                    context.getString(R.string.cannot_use_system)
                            + (operate == 0 ? context
                            .getString(R.string.gallery) : context
                            .getString(R.string.camera)));
        }
    }


    public File getPhotoFileFromResult(BaseActivity context, Intent data) {
        return getPhotoFileFromResult(context, data, false);
    }

    public File getPhotoFileFromResult(BaseActivity context, Intent data,
                                       boolean isOrigin) {
        if (!photoFile.exists() && data != null && data.getData() != null) {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(data.getData(),
                    filePathColumn, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String pickPhotoPath = cursor.getString(columnIndex);
                    try {
                        FileUtils.copyFile(new File(pickPhotoPath), photoFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                cursor.close();
            }
        }
        //confirm the crop image is the original image
        if (photoFile.exists() && !isOrigin) {
//			BitmapUtils.compressBitmap(photoFile, Config.PHOTO_COMPRESS_WIDTH, Config.PHOTO_COMPRESS_HEIGHT);
            compressBitmap(photoFile);
            File tempFile = StorageUtils.getCacheCustomDir(context, "localImg");
            tempFile = new File(tempFile, photoFile.getName().split("\\.")[0] + "_tmp.jpg");
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
        return photoFile;
    }

    protected void compressBitmap(File photoFile) {
        BitmapUtils.compressBitmap(photoFile, Config.PHOTO_COMPRESS_WIDTH, Config.PHOTO_COMPRESS_HEIGHT);
    }

    public File getPhotoFileFromFile(BaseActivity context, File data) {
        //confirm the crop image is the original image
        if (data.exists()) {
            compressBitmap(photoFile);
//			BitmapUtils.compressBitmap(data, Config.PHOTO_COMPRESS_WIDTH,
//					Config.PHOTO_COMPRESS_HEIGHT);
        }
        return data;
    }

    public void doCropImage(BaseActivity context, Intent data, int requestCode) {
        getPhotoFileFromResult(context, data, true);
        if (!photoFile.exists()) return;
        File tempFile = StorageUtils.getCacheCustomDir(context, "localImg");
        tempFile = new File(tempFile, photoFile.getName().split("\\.")[0] + "_tmp.jpg");
        FileUtils.copyFile(photoFile, tempFile);
        context.startActivityForResult(
                AndroidUtils.getCropImageIntent(tempFile, photoFile),
                requestCode);

    }

}