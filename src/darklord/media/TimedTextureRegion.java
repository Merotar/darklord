package darklord.media;

import java.io.Serializable;

import darklord.game.RefillingStore;

public class TimedTextureRegion implements Serializable
{
	public TextureRegion textureRegion;
	public RefillingStore timer;
	
	public TimedTextureRegion(TextureRegion textureRegion, RefillingStore timer) {
		super();
		this.textureRegion = textureRegion;
		this.timer = timer;
	}

	public TextureRegion getTextureRegion() {
		return textureRegion;
	}
	public void setTextureRegion(TextureRegion textureRegion) {
		this.textureRegion = textureRegion;
	}
	public RefillingStore getTimer() {
		return timer;
	}
	public void setTimer(RefillingStore timer) {
		this.timer = timer;
	}
}
