package com.lotsofcode.fishingtrawler;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import javax.inject.Inject;
import net.runelite.api.GameObject;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

public class FishingTrawlerSceneOverlay extends Overlay
{
	private final FishingTrawlerPlugin plugin;
	private final FishingTrawlerConfig config;

	@Inject
	FishingTrawlerSceneOverlay(FishingTrawlerPlugin plugin, FishingTrawlerConfig config)
	{
		this.plugin = plugin;
		this.config = config;
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_SCENE);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (!plugin.isInTrawler())
		{
			return null;
		}

		if (config.highlightLeaks())
		{
			for (GameObject leak : plugin.getLeaks())
			{
				outlineTile(graphics, leak, config.leakColor());
			}
		}

		if (config.highlightHoles())
		{
			for (GameObject hole : plugin.getHoles())
			{
				outlineTile(graphics, hole, config.holeColor());
			}
		}

		return null;
	}

	private void outlineTile(Graphics2D graphics, GameObject obj, Color color)
	{
		Polygon poly = obj.getCanvasTilePoly();
		if (poly == null)
		{
			return;
		}
		OverlayUtil.renderPolygon(graphics, poly, color);
	}
}
