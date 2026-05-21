package com.lotsofcode.fishingtrawler;

import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

public class FishingTrawlerInfoOverlay extends OverlayPanel
{
	private final FishingTrawlerPlugin plugin;
	private final FishingTrawlerConfig config;

	@Inject
	FishingTrawlerInfoOverlay(FishingTrawlerPlugin plugin, FishingTrawlerConfig config)
	{
		this.plugin = plugin;
		this.config = config;
		setPosition(OverlayPosition.TOP_LEFT);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (!plugin.isInTrawler() || !config.showTripPanel())
		{
			return null;
		}

		int ticks = plugin.tripTicks();
		int seconds = (int) Math.round(ticks * 0.6);
		String time = String.format("%d:%02d", seconds / 60, seconds % 60);

		panelComponent.getChildren().clear();
		panelComponent.getChildren().add(TitleComponent.builder()
			.text("Trawler")
			.build());
		panelComponent.getChildren().add(LineComponent.builder()
			.left("Time")
			.right(time)
			.build());
		panelComponent.getChildren().add(LineComponent.builder()
			.left("Leaks")
			.right(String.valueOf(plugin.getLeaks().size()))
			.build());
		if (!plugin.getHoles().isEmpty())
		{
			panelComponent.getChildren().add(LineComponent.builder()
				.left("Holes")
				.right(String.valueOf(plugin.getHoles().size()))
				.build());
		}

		return super.render(graphics);
	}
}
