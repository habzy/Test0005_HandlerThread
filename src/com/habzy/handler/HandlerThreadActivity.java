package com.habzy.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HandlerThreadActivity extends Activity implements OnClickListener {

	protected static final String TAG = "HandlerThreadActivity";

	Handler mUIHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (1 == msg.what) {
				resultTextView.setText((String) msg.obj);
			} else
				super.handleMessage(msg);
		}
	};
	private HandlerThread mHandlerThread = new HandlerThread(
			"TestHandlerThread");
	Handler mHandler;

	private String result = "results:";
	private long sum = 0;

	private TextView resultTextView;

	private Button addButton;

	private Button reduceButton;

	private Button muliplyButton;

	private Button divideButton;

	Runnable add = new Runnable() {

		@Override
		public void run() {
			int i = 20;
			while ((--i) > 0) {
				sum = sum + i;
				result = "result +:" + sum;
				Log.d(TAG, result);
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// Can't like this
				// resultTextView.setText(result);

//				Message msg = Message.obtain(mHandler, 1);
//				msg.obj = result;
				// Can't like this too.... in the sub_thread too.
				// mHandler.handleMessage(msg);
				// Can't like this tooo.... in the sub_thread tooo.
//				mHandler.sendMessage(msg);

				 Message msg = Message.obtain(mUIHandler, 1);
				 msg.obj = result;
				// //Can't like this toooo...in the sub_thread.
				// //mUIHandler.handleMessage(msg);
				 mUIHandler.sendMessage(msg);

			}
		}
	};

	Runnable reduce = new Runnable() {

		@Override
		public void run() {
			int i = 20;
			while ((--i) > 0) {
				sum = sum - i;
				result = "result -:" + sum;
				Log.d(TAG, result);
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Message msg = Message.obtain(mUIHandler, 1);
				msg.obj = result;
				mUIHandler.sendMessage(msg);
			}
		}
	};

	Runnable multiply = new Runnable() {

		@Override
		public void run() {
			int i = 20;
			while ((--i) > 0) {
				sum = sum * 2;
				result = "result *:" + sum;
				Log.d(TAG, result);
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Message msg = Message.obtain(mUIHandler, 1);
				msg.obj = result;
				mUIHandler.sendMessage(msg);
			}
		}
	};

	Runnable divide = new Runnable() {

		@Override
		public void run() {
			int i = 20;
			while ((--i) > 0) {
				sum = sum / i;
				result = "result /:" + sum;
				Log.d(TAG, result);
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Message msg = Message.obtain(mUIHandler, 1);
				msg.obj = result;
				mUIHandler.sendMessage(msg);
			}
		}
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mHandlerThread.start();
		mHandler = new Handler(mHandlerThread.getLooper()) {

			@Override
			public void handleMessage(Message msg) {
				if (1 == msg.what) {
					resultTextView.setText((String) msg.obj);
				} else
					super.handleMessage(msg);
			}
		};

		addButton = (Button) findViewById(R.id.add);
		muliplyButton = (Button) findViewById(R.id.multiply);
		reduceButton = (Button) findViewById(R.id.reduce);
		divideButton = (Button) findViewById(R.id.divide);

		addButton.setOnClickListener(this);
		muliplyButton.setOnClickListener(this);
		reduceButton.setOnClickListener(this);
		divideButton.setOnClickListener(this);

		resultTextView = (TextView) findViewById(R.id.Result);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add: {
			mHandler.post(add);
			break;
		}
		case R.id.multiply: {
			mHandler.post(multiply);
			break;
		}
		case R.id.reduce: {
			mHandler.post(reduce);
			break;
		}
		case R.id.divide: {
			mHandler.post(divide);
			break;
		}
		default:
			break;
		}

	}
}