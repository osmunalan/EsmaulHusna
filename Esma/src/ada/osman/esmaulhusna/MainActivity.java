/**
 *  This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * @author osman
 *
 */

package ada.osman.esmaulhusna;

import ada.osman.esmaulhusna.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;

import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class MainActivity extends Activity implements OnClickListener {

	private static int a = 1;
	private Display mDisplay;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		/*
		 * Uygulama ilk açıldığında SharedPref'den sayaç alınıyor. Oryantasyon
		 * değiştikce onCreate tekrar çağırıldığı için böyle bir işlem yapıldı.
		 * onCreate bir defa çağırılıp ayrı bir listener ile bu işlemi yapmak
		 * daha sağlıklı.
		 */
		if (a == 1) {
			SharedPreferences prefGet = getSharedPreferences("SYC",
					Activity.MODE_PRIVATE);
			a = prefGet.getInt("syc", 1);
		}

		Button b1 = (Button) findViewById(R.id.ilerib);
		b1.setOnClickListener(this);
		Button b2 = (Button) findViewById(R.id.gerib);
		b2.setOnClickListener(this);

		Button b3 = (Button) findViewById(R.id.ilkb);
		b3.setOnClickListener(this);
		Button b4 = (Button) findViewById(R.id.sonb);
		b4.setOnClickListener(this);
		Button b5 = (Button) findViewById(R.id.gitb);
		b5.setOnClickListener(this);
		TextView baslik = (TextView) findViewById(R.id.baslik);
		baslik.setOnClickListener(this);

		/*
		 * Buttonların renklerini değiştirme. Bu renklerde button oluşturulup
		 * yapılması daha sağlıklı.
		 */
		b1.getBackground().setColorFilter(0xFFE0FFFF, Mode.MULTIPLY);
		b2.getBackground().setColorFilter(0xFFE0FFFF, Mode.MULTIPLY);

		b3.getBackground().setColorFilter(0xFFEAF1D5, Mode.MULTIPLY);
		b4.getBackground().setColorFilter(0xFFEAF1D5, Mode.MULTIPLY);

		/*
		 * Ekranın oryantasyonuna göre layout üzerindeki buttonları set etmek
		 * işlemi
		 */
		try {
			WindowManager mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
			mDisplay = mWindowManager.getDefaultDisplay();
			if (mDisplay.getOrientation() == 1
					|| mDisplay.getOrientation() == 3) {
				Button b12 = (Button) findViewById(R.id.ilerib2);
				b12.setOnClickListener(this);
				Button b22 = (Button) findViewById(R.id.gerib2);
				b22.setOnClickListener(this);
				b12.getBackground().setColorFilter(0xFFE0FFFF, Mode.MULTIPLY);
				b22.getBackground().setColorFilter(0xFFE0FFFF, Mode.MULTIPLY);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		showImage();

	}

	public static int getA() {

		return a;
	}

	public static void setA(int b) {

		a = b;
	}

	/* Menu oluşturna işlemleri */
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater myInf = getMenuInflater();
		myInf.inflate(R.layout.options_menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menuHakkinda:
			AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
			alertBuilder.setMessage(R.string.about).setNegativeButton("Tamam",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// Action for 'Tamam' Button
							dialog.cancel();
						}
					});
			AlertDialog alert = alertBuilder.create();
			// Title for AlertDialog
			alert.setTitle("Hakkında");

			alert.show();

			return true;
		case R.id.menuExit:
			finish();
			return true;
		case R.id.menuEsma:
			Intent intent = new Intent();
			intent = new Intent(MainActivity.this,
					ada.osman.esmaulhusna.Arabi.class);
			startActivity(intent);
			return true;
		}

		return false;

	}

	/* Program sonlandığında kalınan sayıyı Shared Pref'e kaydet */
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		SharedPreferences prefPut = getSharedPreferences("SYC",
				Activity.MODE_PRIVATE);
		Editor prefEditor = prefPut.edit();
		prefEditor.putInt("syc", a);
		prefEditor.commit();

	}

	/* Ekranı yeni bilgiler ile doldurma işlemi */
	public void showImage() {
		if (a <= 0)
			a = 99;
		else if (a >= 100)
			a = 1;

		String uri = "drawable/i" + a;
		String uri2 = "string/n" + a;
		String uri3 = "string/s" + a;
		Log.d("a", a + " " + uri + uri2 + uri3);
		// int imageResource = R.drawable.icon;
		int imageResource = getResources().getIdentifier(uri, null,
				"ada.osman.esmaulhusna");

		ImageView imageView = (ImageView) findViewById(R.id.im1);
		Drawable image = getResources().getDrawable(imageResource);
		imageView.setImageDrawable(image);

		TextView t = (TextView) findViewById(R.id.syc);
		t.setText("" + a);

		int nameres = getResources().getIdentifier(uri2, null,
				"ada.osman.esmaulhusna");
		String name = getResources().getString(nameres);
		TextView t2 = (TextView) findViewById(R.id.baslik);
		t2.setText(name);

		nameres = getResources().getIdentifier(uri3, null,
				"ada.osman.esmaulhusna");
		String aciklama = getResources().getString(nameres);
		TextView t3 = (TextView) findViewById(R.id.aciklama);
		t3.setText(aciklama);
		((ScrollView) findViewById(R.id.scrollview)).scrollTo(0, 0);

	}

	public String getStringResourceByName(String aString) {

		int resId = getResources().getIdentifier(aString, "string",
				"ada.osman.esmaulhusna");
		return getString(resId);
	}

	/* Buttonlar ve başlık için onClick */
	public void onClick(View v) {
		ViewFlipper vf = (ViewFlipper) findViewById(R.id.details);
		if (v.getId() == R.id.ilerib || v.getId() == R.id.ilerib2) {
			a++;
			vf.setAnimation(AnimationUtils.loadAnimation(this,
					R.anim.slide_left));
			showImage();

		} else if (v.getId() == R.id.gerib || v.getId() == R.id.gerib2) {
			a--;
			vf.setAnimation(AnimationUtils.loadAnimation(this,
					R.anim.slide_right));
			showImage();

		} else if (v.getId() == R.id.ilkb) {
			a = 1;
			showImage();
			vf.setAnimation(AnimationUtils.loadAnimation(this,
					R.anim.slide_left));

		} else if (v.getId() == R.id.sonb) {
			a = 99;
			showImage();
			vf.setAnimation(AnimationUtils.loadAnimation(this,
					R.anim.slide_right));

		} else if (v.getId() == R.id.gitb) {

			try {
				EditText e1 = (EditText) findViewById(R.id.git1);
				Editable e2 = e1.getText();

				vf.setAnimation(AnimationUtils.loadAnimation(this,
						R.anim.slide_left));
				a = Integer.parseInt(e2.toString());
				showImage();
				e1.setText("");
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(e1.getWindowToken(), 0);

			} catch (Exception e) {

			}

		} else if (v.getId() == R.id.baslik) {
			if (mDisplay.getOrientation() == 0)
				if (findViewById(R.id.im1).isShown())
					((ImageView) findViewById(R.id.im1))
							.setVisibility(View.GONE);
				else
					((ImageView) findViewById(R.id.im1))
							.setVisibility(View.VISIBLE);
			else if (mDisplay.getOrientation() == 1
					|| mDisplay.getOrientation() == 3)
				if (findViewById(R.id.solyanım).isShown()) {
					((LinearLayout) findViewById(R.id.solyanım))
							.setVisibility(View.GONE);
					((LinearLayout) findViewById(R.id.yedek))
							.setVisibility(View.VISIBLE);
				} else {
					((LinearLayout) findViewById(R.id.solyanım))
							.setVisibility(View.VISIBLE);
					((LinearLayout) findViewById(R.id.yedek))
							.setVisibility(View.GONE);
				}
		}

	}

}