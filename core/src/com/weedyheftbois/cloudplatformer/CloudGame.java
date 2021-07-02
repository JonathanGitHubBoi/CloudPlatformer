package com.weedyheftbois.cloudplatformer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class CloudGame extends ApplicationAdapter {
	private OrthographicCamera camera;
	SpriteBatch batch;
	private Texture cloudTex;
	private Rectangle cloudWRec;
	public float launchV = 32;
	public boolean jump = false;
	private float jump_start;
	private Texture boxTex;
	private Rectangle BoxRec;
	private Array<Rectangle> box_array;
	boolean boxTouchW = true;


	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false,1440, 900);
		cloudWRec = initCloudW();
		batch = new SpriteBatch();
		box_array = new Array<Rectangle>();
		cloudTex = new Texture(Gdx.files.internal("CWFill_in.png"));
		// renderBox();
		//BoxTex = new Texture(Gdx.files.internal("Box.png"));

		int[][] world1 = new int[144][90];
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

	public void worldbuilder(int[][] world1) {

		world1[0][89] = 1;
		world1[0][50] = 1;
		//System.out.print(Arrays.deepToString(world1));
		int x = 0;
		int y = 0;
			for (int[] world_height : world1) {
				for (int world_width : world_height) {
					if (world_width == 1){
						int tile_scale = 10;
						box_array.add(renderBox(x * tile_scale, y * tile_scale));
					}
					y++;
				}
				x ++;
			}
		}

	private void renderBoxArray(SpriteBatch batchIn){
		for (Rectangle box : box_array){
	batchIn.draw(boxTex, box.x, box.y);
		}
	}
	private Rectangle renderBox(int Box_real_x, int Box_real_y){
		Rectangle Box = new Rectangle();
		Box.x = Box_real_x;
		Box.y = Box_real_y;
		Box.width = 10;
		Box.height = 10;
		boxTex = new Texture(Gdx.files.internal("Box.png"));
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
		batch.draw(cloudTex, cloudWRec.x, cloudWRec.y);
	//	batch.draw(BoxTex, BoxRec.x, BoxRec.y);
		renderBoxArray(batch);
		for (Rectangle box : box_array){
			if (box.overlaps(cloudWRec)){
				boxTouchW = true;
			}
			else boxTouchW = false;
		}



		//No new draws below


		batch.end();
		camera.update();
		checkInput();
	}


	private void checkInput() {


		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
			cloudWRec.x -= 200 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
			cloudWRec.x += 200 * Gdx.graphics.getDeltaTime();
		if (cloudWRec.x < 0) cloudWRec.x = 0;
		if (cloudWRec.x > 1440 - 40) cloudWRec.x = 1440 - 40;
		if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
			jump_start = TimeUtils.nanoTime();
			jump = true;
		}
		if (jump == true) {

			float t =  (TimeUtils.nanoTime() - jump_start ) / 1000000000f;
			cloudWRec.y += -50 * (t * t) + launchV * (t);
		}
		if (cloudWRec.y < 0 || ) {
			jump = false;
			cloudWRec.y = 1;
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
		cloudTex.dispose();
		batch.dispose();

	}
}
