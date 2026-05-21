package com.lotsofcode.fishingtrawler;

import com.google.inject.Provides;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.GameState;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameObjectDespawned;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(
	name = "Fishing Trawler Companion",
	description = "Highlights leaks and tracks trip progress during the Fishing Trawler minigame",
	tags = {"fishing", "trawler", "minigame", "skilling"}
)
public class FishingTrawlerPlugin extends Plugin
{
	// Fishing Trawler boat regions. Verify against current map data and replace
	// with gameval region constants if/when they're added upstream.
	private static final Set<Integer> TRAWLER_REGIONS = Set.of(7499, 7757, 7758);

	// Hull leak object IDs. TODO: replace with net.runelite.api.gameval.ObjectID
	// constants once the exact gameval names are verified for the current API.
	private static final Set<Integer> LEAK_IDS = Set.of(2167, 2168);

	// Large hull breach IDs (require a plank, not just a bucket).
	private static final Set<Integer> HOLE_IDS = Set.of(2169, 2170);

	@Inject private Client client;
	@Inject private FishingTrawlerConfig config;
	@Inject private OverlayManager overlayManager;
	@Inject private FishingTrawlerSceneOverlay sceneOverlay;
	@Inject private FishingTrawlerInfoOverlay infoOverlay;

	@Getter(AccessLevel.PACKAGE)
	private final Set<GameObject> leaks = new HashSet<>();

	@Getter(AccessLevel.PACKAGE)
	private final Set<GameObject> holes = new HashSet<>();

	@Getter(AccessLevel.PACKAGE)
	private boolean inTrawler = false;

	private int tripStartTick = -1;

	@Override
	protected void startUp()
	{
		overlayManager.add(sceneOverlay);
		overlayManager.add(infoOverlay);
		log.debug("Fishing Trawler Companion started");
	}

	@Override
	protected void shutDown()
	{
		overlayManager.remove(sceneOverlay);
		overlayManager.remove(infoOverlay);
		resetSession();
		log.debug("Fishing Trawler Companion stopped");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		switch (event.getGameState())
		{
			case LOGGED_IN:
				refreshLocation();
				break;
			case LOGIN_SCREEN:
			case HOPPING:
				resetSession();
				break;
			default:
				break;
		}
	}

	@Subscribe
	public void onGameTick(GameTick tick)
	{
		refreshLocation();
	}

	@Subscribe
	public void onGameObjectSpawned(GameObjectSpawned event)
	{
		if (!inTrawler)
		{
			return;
		}
		GameObject obj = event.getGameObject();
		int id = obj.getId();
		if (LEAK_IDS.contains(id))
		{
			leaks.add(obj);
		}
		else if (HOLE_IDS.contains(id))
		{
			holes.add(obj);
		}
	}

	@Subscribe
	public void onGameObjectDespawned(GameObjectDespawned event)
	{
		leaks.remove(event.getGameObject());
		holes.remove(event.getGameObject());
	}

	int tripTicks()
	{
		if (tripStartTick < 0)
		{
			return 0;
		}
		return client.getTickCount() - tripStartTick;
	}

	private void refreshLocation()
	{
		if (client.getLocalPlayer() == null)
		{
			return;
		}
		WorldPoint wp = client.getLocalPlayer().getWorldLocation();
		if (wp == null)
		{
			return;
		}
		boolean nowOn = TRAWLER_REGIONS.contains(wp.getRegionID());
		if (nowOn && !inTrawler)
		{
			inTrawler = true;
			tripStartTick = client.getTickCount();
			log.debug("Entered Fishing Trawler at region {}", wp.getRegionID());
		}
		else if (!nowOn && inTrawler)
		{
			resetSession();
		}
	}

	private void resetSession()
	{
		inTrawler = false;
		tripStartTick = -1;
		leaks.clear();
		holes.clear();
	}

	@Provides
	FishingTrawlerConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(FishingTrawlerConfig.class);
	}
}
