package net.sourcedreams;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory.Default;

public class GameResources implements Disposable {
	
	private static GameResources instance;
	
	/**
	 * Forces initialization of GameResources, otherwise they will be lazily 
	 * instantiated (all at once) upon first call to GameResources.getInstance().
	 */
	public static void Initialize(){
		if (instance == null){
			instance = new GameResources();
		}
	}
	
	public static GameResources getInstance(){
		Initialize();
		return instance;
	}
	
	public static void Dispose(){
		if (instance != null){
			instance.dispose();
		}
	}
	
	private GameResources(){
		textureRegions = new GameTextures();
		assetManager = new AssetManager();
		pixmapPacker = new PixmapPacker(512, 512, Format.RGBA4444, 0, false);
		developmentAtlas = new TextureAtlas();
		spriteBatch = new SpriteBatch();
		splashFont = new BitmapFont(Gdx.files.internal("ui/AgencyFB-160.fnt"), false);
		defaultFont = new BitmapFont(Gdx.files.internal("ui/AgencyFB-32.fnt"), false);
		
		assetManager.load("audio/beat.ogg", Music.class);
		assetManager.load("ui/uiskin.json", Skin.class);
		
//		skin = new Skin(Gdx.files.internal("ui/uiskin.json"));		
//		beat = Gdx.audio.newMusic(Gdx.files.internal("audio/beat.ogg"));
		
	}
	
	private final GameTextures textureRegions;
	private final AssetManager assetManager;
	private final PixmapPacker pixmapPacker;
	private TextureAtlas developmentAtlas;
	private final SpriteBatch spriteBatch;
	private Skin skin;
	private BitmapFont defaultFont;
	private BitmapFont splashFont;
	private Music beat;

	private boolean developmentAtlasComplete = false;
	private boolean loadingComplete = false;
	
	public static AssetManager getAssetManager(){
		Initialize();
		return instance.assetManager; 
	}
	
	public static SpriteBatch getSpriteBatch(){
		Initialize();
		return instance.spriteBatch;
	}
	
	public static Music getMusic(){
		Initialize();
		if (instance.beat == null){
			if (instance.assetManager.isLoaded("audio/beat.ogg")){
				instance.beat = instance.assetManager.get("audio/beat.ogg");
			} else return null;
		} 
		return instance.beat;
	}	
	
	public static Skin getSkin(){
		Initialize();
		if (instance.skin == null){
			if (instance.assetManager.isLoaded("ui/uiskin.json")){
				instance.skin = instance.assetManager.get("ui/uiskin.json");
			} else return null;
		}
		return instance.skin;
	}
	
	public static GameTextures getGameTextures(){
		Initialize();
		return instance.textureRegions;
	}
	
	public static BitmapFont getDefaultFont(){
		Initialize();
		return instance.defaultFont;
	}
	
	public static BitmapFont getSplashFont(){
		Initialize();
		return instance.splashFont;
	}
 
	@Override
	public void dispose() {
		assetManager.dispose();

//		assumedly taken care of by assetManager.dispose()
//		skin.dispose();
//		beat.dispose();
		
		defaultFont.dispose();
		splashFont.dispose();
		
		spriteBatch.dispose();
		developmentAtlas.dispose();
		instance = null;
		
		Gdx.app.log("GameResources", "Disposed!");
	}

	public static boolean LoadStep() {
		Initialize();
		return instance.loadStep();
	}
	
	private boolean loadStep() {
		if (loadingComplete ) return true;
		
		/*
		 * Development only - Release builds will have a pre-made pixmap
		 */
		if(!developmentAtlasComplete){
			pixmapPacker.pack("testComponent", new Pixmap(Gdx.files.internal("images/testComponent.png")));
			pixmapPacker.pack("selection", new Pixmap(Gdx.files.internal("images/selection.png")));
			pixmapPacker.pack("bandingBoxIcon", new Pixmap(Gdx.files.internal("images/bandingBoxIcon.png")));
			developmentAtlas = pixmapPacker.generateTextureAtlas(TextureFilter.Linear, TextureFilter.Linear, false);
			
			textureRegions.testComponent = developmentAtlas.findRegion("testComponent");
			textureRegions.selection = new NinePatch(developmentAtlas.findRegion("selection"),6,6,6,6);
			textureRegions.bandingBoxIcon = developmentAtlas.findRegion("bandingBoxIcon");
			
			developmentAtlasComplete = true;
		}
		
		if (assetManager.update()){
			loadingComplete=true;
			return true;
		} else return false;
	}

	public static float getPercentLoaded() {
		Initialize();
		return 100f*instance.assetManager.getProgress();
	}

	public static boolean isLoadingComplete(){
		Initialize();
		return instance.loadingComplete;
	}
	
	public class GameTextures{
		public TextureRegion testComponent;
//		public TextureRegion selected;
		public NinePatch selection;
		public TextureRegion bandingBoxIcon;
	}
}
