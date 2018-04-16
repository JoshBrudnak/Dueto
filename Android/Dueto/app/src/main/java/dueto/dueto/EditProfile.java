package dueto.dueto;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class EditProfile extends AppCompatActivity {
    private EditText name;
    private EditText username;
    private EditText website;
    private EditText location;
    private EditText description;
    private TextView changePic;
    private ImageView profilePic;

    private Bitmap bitmap;

    private TextView defaultName;
    private EditText defaultUsername;
    private TextView defaultWebsite;
    private TextView defaultLocation;

    Integer REQUEST_CAMERA=1, SELECT_FILE=0;
    private static final String TAG = "ProfilePicPreview";

    private Button Save;
    private Button SignOut;
    private TextView Cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        name = (EditText) findViewById(R.id.nameChangeText);
        username = (EditText) findViewById(R.id.userChangeText);
        website = (EditText) findViewById(R.id.webChangeText);
        location = (EditText) findViewById(R.id.locChangeText);
        description = (EditText) findViewById(R.id.descChangeText);
        changePic = (TextView) findViewById(R.id.editPicButton);
        profilePic = (ImageView) findViewById(R.id.iconChangeView);

        bitmap = BitmapFactory.decodeResource(getResources(), R.id.iconChangeView);

        Cancel = (TextView) findViewById(R.id.cancelChangeView);
        Save = (Button) findViewById(R.id.saveProfileChange);
        SignOut = (Button) findViewById(R.id.signOut);

        changePic.setOnClickListener(new View.OnClickListener() { //calls onClick(default name) through ClickListener which takes you to LoginActivity
            @Override
            public void onClick(View view) {

                SelectImage();
            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameText = name.getText().toString();
                String locText = location.getText().toString();
                String descText = description.getText().toString();

                Intent myIntent = new Intent(view.getContext(), UserProfileActivity.class);
                myIntent.putExtra("name", nameText);
                myIntent.putExtra("location", locText);
                myIntent.putExtra("description", descText);

                profilePic.buildDrawingCache();
                Bitmap bitmap = profilePic.getDrawingCache();
                ByteArrayOutputStream _bs = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, _bs);
                myIntent.putExtra("byteArray", _bs.toByteArray());

                startActivity(myIntent);
            }
        });

        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditProfile.this, FirstActivity.class));
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditProfile.this, UserProfileActivity.class));
            }
        });

    }

    public int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    public void ChangeFragment(View view) {
        Fragment fragment;

        if(view == findViewById(R.id.button1)) {
            fragment = new PostsFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_place, fragment);
            ft.commit();
        }
        else if (view == findViewById(R.id.button2)) {
            fragment = new RepostsFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_place, fragment);
            ft.commit();
        }
        else {
            fragment = new PostsFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_place, fragment);
            ft.commit();
        }

    }

    private void SelectImage(){

        final CharSequence[] items={"Camera","Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);
        builder.setTitle("Add Image");

        Log.d(TAG, "SelectImage: jjjjjjjjjjj");

        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Camera")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);

                } else if (items[i].equals("Gallery")) {

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent, SELECT_FILE);

                } else if (items[i].equals("Cancel")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();

    }

    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);

        if(resultCode== Activity.RESULT_OK){

            if(requestCode==REQUEST_CAMERA){

                Bundle bundle = data.getExtras();
                Bitmap bmp = (Bitmap) bundle.get("data");
                bmp = Bitmap.createScaledBitmap(bmp, 200, 200, false);
                profilePic.setImageBitmap(bmp);

            }else if(requestCode==SELECT_FILE){

                //Uri selectedImageUri = data.getData();

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                cursor.close();

                Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);

                ExifInterface exif = null;
                try {
                    File pictureFile = new File(filePath);
                    exif = new ExifInterface(pictureFile.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                int orientation = ExifInterface.ORIENTATION_NORMAL;

                if (exif != null){
                    orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            yourSelectedImage = rotateBitmap(yourSelectedImage, 90);
                            yourSelectedImage = Bitmap.createScaledBitmap(yourSelectedImage, 188, 188, false);

                            profilePic.setImageBitmap(yourSelectedImage);
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            yourSelectedImage = rotateBitmap(yourSelectedImage, 180);
                            yourSelectedImage = Bitmap.createScaledBitmap(yourSelectedImage, 188, 188, false);

                            profilePic.setImageBitmap(yourSelectedImage);
                            break;

                        case ExifInterface.ORIENTATION_ROTATE_270:
                            yourSelectedImage = rotateBitmap(yourSelectedImage, 270);
                            yourSelectedImage = Bitmap.createScaledBitmap(yourSelectedImage, 188, 188, false);

                            profilePic.setImageBitmap(yourSelectedImage);
                            break;
                    }
                }

            }

        }

    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap decodeUri(Context c, Uri uri, final int requiredSize) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o);

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;

        while(true) {
            if(width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_profile_pic, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
