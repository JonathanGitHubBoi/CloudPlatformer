package com.weedyheftbois.cloudplatformer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Arrays;
import java.util.Iterator;

import static java.lang.System.currentTimeMillis;

public class CloudGame extends ApplicationAdapter {
	private OrthographicCamera camera;
	SpriteBatch batch;
	private Texture CloudTex;
	private Rectangle CloudWRec;
	public float LaunchV = 32;
	public boolean jump = false;
	private float jump_start;
	private Texture BoxTex;
	private Rectangle BoxRec;


	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false,1440, 900);
		CloudWRec = initCloudW();
		batch = new SpriteBatch();
		//raindrops = new Array<Rectangle>();
		CloudTex = new Texture(Gdx.files.internal("CWFill_in.png"));
		renderBox();
		BoxTex = new Texture(Gdx.files.internal("Box.png"));
		BoxRec = renderBox();
		int[][] world1 = new int[10][10];
		worldbuilder(world1);


	//	spawnRaindrop();
	}

	private Rectangle initCloudW(){
		Rectangle CloudWOut = new Rectangle();
		CloudWOut.x = 0;
		CloudWOut.y = 0;
		CloudWOut.width = 20;
		CloudWOut.height = 100;
		return CloudWOut;
	}
	public static void main(String[] args) {

	}

	public static void worldbuilder(int[][] world1) {
		world1[0][0] = 1;
		System.out.print(Arrays.deepToString(world1));
	}
	private Rectangle renderBox(){
		Rectangle Box = new Rectangle();
		Box.x = 480;
		Box.y = 480;
		Box.width = 10;
		Box.height = 10;
		return Box;
	}


	@Override
	public void render () {
		ScreenUtils.clear(0, 0, .2f, 1);
	//	doRaindropSpawn();
	//	moveRaindrops();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
	//	renderRaindrops(batch);
		batch.draw(CloudTex, CloudWRec.x, CloudWRec.y);
		batch.draw(BoxTex, BoxRec.x, BoxRec.y);



		//No new draws below


		batch.end();
		camera.update();
		checkInput();
	}


	private void checkInput() {


		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
			CloudWRec.x -= 200 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
			CloudWRec.x += 200 * Gdx.graphics.getDeltaTime();
		if (CloudWRec.x < 0) CloudWRec.x = 0;
		if (CloudWRec.x > 1440 - 40) CloudWRec.x = 1440 - 40;
		if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
			jump_start = TimeUtils.nanoTime();
			jump = true;
		}
		if (jump == true) {

			float t =  (TimeUtils.nanoTime() - jump_start ) / 1000000000f;
			CloudWRec.y += -50 * (t * t) + LaunchV * (t);
		}
		if (CloudWRec.y < 0) {
			jump = false;
			CloudWRec.y = 1;
		}


	}


/*	private void doRaindropSpawn() {
		if (TimeUtils.nanoTime() - last_drop_time > 1000000000) spawnRaindrop();
	}

	private void renderRaindrops(SpriteBatch batchIn){
		for (Rectangle raindrop : raindrops){
			batchIn.draw(drop_image, raindrop.x, raindrop.y);
		}
	}

	private void moveRaindrops() {
		for (Iterator<Rectangle> iter = raindrops.iterator(); iter.hasNext();){
			Rectangle raindrop = iter.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if (raindrop.y + 64 < 0) iter.remove();

		}
	}


	private void spawnRaindrop(){
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 800-64);
		raindrop.y = 480;
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
		last_drop_time = TimeUtils.nanoTime();
	}
*/
	@Override
	public void dispose () {
		CloudTex.dispose();
		batch.dispose();

	}
}
