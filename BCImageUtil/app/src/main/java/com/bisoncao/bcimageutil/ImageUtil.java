package com.bisoncao.bcimageutil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.bisoncao.bccommonutil.BCDebug;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 图片处理相关类
 *
 * @created 6:23 PM 09/11/2015
 * @author Bison Cao
 */
@SuppressLint("SimpleDateFormat")
public class ImageUtil {

    // 图片保存到SD卡根目录下的一级目录
    public static final String SD_PATH_SAVE_PIC = "BCImageUtil";

    public static final int DEFAULT_MAX_SIZE_PIC = 640;
    public static final int DEFAULT_MAX_SIZE_PIC_PREVIEW = 1280;
    public static final int DEFAULT_MAX_SIZE_PIC_1080 = 1080;
    public static final int DEFAULT_SIZE_THUMBNAIL = 200; // 缩略图生成的默认大小
    public static final int DEFAULT_QUALITY_SAVE_PIC = 85; // 图片保存的默认质量

    /**
     * 根据最大尺寸取样读取原图（避免读取整个原图以节省内存） 注意取样后的图片尺寸可能比最大尺寸大（因为取样基于几分之一），因此为了进一步
     * 节省内存，应该再调用{@link ImageUtil#scale(Bitmap, int)}进行可能的图片缩小
     *
     * @param photoPath
     * @param maxSize   限制较小边的最大值
     * @return
     */
    public static Bitmap sampleRead(String photoPath, int maxSize) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(photoPath, options);// 此时返回的是空
            // 计算缩放比
            int width = options.outWidth;
            int height = options.outHeight;
            Log.d(BCDebug.BISON, "sampleRead ori size: " + width + " * "
                    + height);
            int be = (int) (Math.min(width, height) / (float) maxSize);
            if (be <= 0) {
                be = 1;
            }
            options.inSampleSize = be;
            Log.d(BCDebug.BISON, "sampleRead be: " + be);
            // 重新读入图片，注意这次要把options.inJustDecodeBounds设为false哦
            options.inJustDecodeBounds = false;
            Bitmap resultBmp = BitmapFactory.decodeFile(photoPath, options);
            if (resultBmp != null) {
                Log.d(BCDebug.BISON,
                        "sampleRead result size: " + resultBmp.getWidth()
                                + " * " + resultBmp.getHeight());
            }
            return resultBmp;

        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 根据最大尺寸取样读取原图（避免读取整个原图以节省内存） 注意取样后的图片尺寸可能比最大尺寸大（因为取样基于几分之一），因此为了进一步
     * 节省内存，应该再调用{@link ImageUtil#scaleSmall(Bitmap, int)}进行可能的图片缩小
     *
     * @param photoPath
     * @param maxSize   限制较大边的最大值
     * @return
     */
    public static Bitmap sampleReadSmall(String photoPath, int maxSize) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(photoPath, options);// 此时返回的是空
            // 计算缩放比
            int width = options.outWidth;
            int height = options.outHeight;
            Log.d(BCDebug.BISON, "sampleRead ori size: " + width + " * "
                    + height);
            int be = (int) (Math.max(width, height) / (float) maxSize);
            if (be <= 0) {
                be = 1;
            }
            options.inSampleSize = be;
            Log.d(BCDebug.BISON, "sampleRead be: " + be);
            // 重新读入图片，注意这次要把options.inJustDecodeBounds设为false哦
            options.inJustDecodeBounds = false;
            Bitmap resultBmp = BitmapFactory.decodeFile(photoPath, options);
            if (resultBmp != null) {
                Log.d(BCDebug.BISON,
                        "sampleRead result size: " + resultBmp.getWidth()
                                + " * " + resultBmp.getHeight());
            }
            return resultBmp;

        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 缩放图片，超过最大尺寸则缩小，不超过则返回原图
     * 【注意】
     * 必须把返回值赋回给参数中原图的引用变量，因为原图会被回收
     *
     * @param bmp
     * @param maxSize 限制较小边的最大值
     * @return
     */
    public static Bitmap scale(Bitmap bmp, int maxSize) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        try {
            float scale = (float) maxSize / (Math.min(width, height));
            if (scale > 1) {
                scale = 1;
            }

            // 超过则对其进行尺寸缩小（不超过则不进行任何操作）
            if (scale != 1) {
                Matrix matrix = new Matrix();
                // 缩放图片操作
                matrix.postScale(scale, scale);
                Bitmap resizedBitmap = Bitmap.createBitmap(bmp, 0, 0, width,
                        height, matrix, true);
                if (resizedBitmap != bmp) {
                    bmp.recycle();
                    Log.d(BCDebug.BISON, "bmp is recycled");
                }
                bmp = resizedBitmap;

            }

            return bmp;

        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 缩放图片，超过最大尺寸则缩小，不超过则返回原图
     * 【注意】
     * 必须把返回值赋回给参数中原图的引用变量，因为原图会被回收
     *
     * @param bmp
     * @param maxSize 限制较大边的最大值
     * @return
     */
    public static Bitmap scaleSmall(Bitmap bmp, int maxSize) {
        // 参数合法性检查
        if (bmp == null || maxSize <= 0) {
            return null;
        }

        int width = bmp.getWidth();
        int height = bmp.getHeight();
        try {
            float scale = (float) maxSize / (Math.max(width, height));
            if (scale > 1) {
                scale = 1;
            }

            // 超过则对其进行尺寸缩小（不超过则不进行任何操作）
            if (scale != 1) {
                Matrix matrix = new Matrix();
                // 缩放图片操作
                matrix.postScale(scale, scale);
                Bitmap resizedBitmap = Bitmap.createBitmap(bmp, 0, 0, width,
                        height, matrix, true);
                if (resizedBitmap != bmp) {
                    bmp.recycle();
                    Log.d(BCDebug.BISON, "bmp is recycled");
                }
                bmp = resizedBitmap;

            }

            return bmp;

        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 从文件获取合适大小的图片
     * （使用 {@link ImageUtil#sampleRead(String, int)} 和 {@link ImageUtil#scale(Bitmap, int)}）
     */
    public static Bitmap getFitPic(String photoPath, int maxSize) {
        Bitmap bitmap = sampleRead(photoPath, maxSize);
        if (bitmap != null) {
            return scale(bitmap, maxSize);
        } else {
            return null;
        }
    }

    /**
     * 从文件获取合适大小的图片
     * （使用 {@link ImageUtil#sampleReadSmall(String, int)} 和 {@link ImageUtil#scaleSmall(Bitmap, int)}）
     */
    public static Bitmap getFitPicSmall(String photoPath, int maxSize) {
        Bitmap bitmap = sampleReadSmall(photoPath, maxSize);
        if (bitmap != null) {
            return scaleSmall(bitmap, maxSize);
        } else {
            return null;
        }
    }

    /**
     * 从指定的图片文件路径获取图片的尺寸，返回的依次为宽、高
     *
     * @param photoPath
     * @return
     */
    public static int[] getPicSize(String photoPath) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(photoPath, options);// 此时返回的是空

            return new int[]{options.outWidth, options.outHeight};

        } catch (Exception e) {
            return new int[]{0, 0};
        }

    }

    /**
     * 旋转Bitmap 【注意】必须把返回值赋回给参数中原图的引用变量，因为原图会被回收
     *
     * @param bmp
     * @param degree    非负数
     * @param clockWise 是否顺时针
     * @return 成功返回修改后的参数中的bmp对象，失败返回null
     */
    public static Bitmap rotate(Bitmap bmp, int degree, boolean clockWise) {
        if (bmp == null) {
            return null;
        }
        if (degree < 0) {
            degree = -degree;
        }
        if (!clockWise) {
            degree = -degree;
        }
        try {
            int width = bmp.getWidth();
            int height = bmp.getHeight();
            // 创建操作图片用的matrix对象
            Matrix matrix = new Matrix();
            // 旋转图片 动作
            matrix.postRotate(degree);
            // 创建新的图片
            Bitmap resizedBitmap = Bitmap.createBitmap(bmp, 0, 0, width,
                    height, matrix, true);
            if (resizedBitmap != bmp) {
                bmp.recycle();
                Log.d(BCDebug.BISON, "bmp is recycled");
            }
            bmp = resizedBitmap;

            return bmp;

        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 从Bitmap生成缩略图并回收原图
     *
     * @param bitmap
     * @param width
     * @param height
     * @return
     */
    public static Bitmap getThumbAndRecycle(Bitmap bitmap, int width, int height) {
        return ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
    }

    public static Bitmap getThumbAndRecycle(Bitmap bitmap, int sideLen) {
        return getThumbAndRecycle(bitmap, sideLen, sideLen);
    }

    public static Bitmap getThumbAndRecycle(Bitmap bitmap) {
        return getThumbAndRecycle(bitmap, DEFAULT_SIZE_THUMBNAIL, DEFAULT_SIZE_THUMBNAIL);
    }

    public static Bitmap getThumbWithoutRecycle(Bitmap bitmap, int width, int height) {
        return ThumbnailUtils.extractThumbnail(bitmap, width, height);
    }

    public static Bitmap getThumbWithoutRecycle(Bitmap bitmap, int sideLen) {
        return getThumbWithoutRecycle(bitmap, sideLen, sideLen);
    }

    public static Bitmap getThumbWithoutRecycle(Bitmap bitmap) {
        return getThumbWithoutRecycle(bitmap, DEFAULT_SIZE_THUMBNAIL, DEFAULT_SIZE_THUMBNAIL);
    }

    /**
     * 以时间为文件名保存到 {@link ImageUtil#SD_PATH_SAVE_PIC} 目录下
     *
     * @param bmp
     * @return 成功时返回保存的文件绝对路径，失败时返回null
     */
    public static String saveBitmapToFile(Bitmap bmp) {
        try {
            // 创建文件
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");
            String fileName = sdf.format(new Date(System.currentTimeMillis()))
                    + ".jpg";
            StringBuilder path = new StringBuilder();
            path.append(Environment.getExternalStorageDirectory());
            path.append(File.separator).append(SD_PATH_SAVE_PIC)
                    .append(File.separator);
            File dir = new File(path.toString());
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File f = new File(path.toString() + fileName);
            if (f.exists()) {
                f.delete();
            }

            try {
                f.createNewFile();

            } catch (Exception e) {
                return null;
            }

            // 保存Bitmap
            FileOutputStream fOut = null;
            try {
                fOut = new FileOutputStream(f);

            } catch (Exception e) {
                return null;
            }
            bmp.compress(Bitmap.CompressFormat.JPEG, DEFAULT_QUALITY_SAVE_PIC,
                    fOut);
            try {
                fOut.flush();
                return f.getPath();

            } catch (Exception e) {
                return null;

            } finally {
                if (fOut != null) {
                    try {
                        fOut.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

        } catch (Exception e) {
            return null;
        }

    }

    public static String getImgFilePathFromUri(Activity activity, Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor actualimagecursor = activity.managedQuery(uri, proj, null, null,
                null);
        int actual_image_column_index = actualimagecursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        String img_path = actualimagecursor
                .getString(actual_image_column_index);
        return img_path;
    }

}
