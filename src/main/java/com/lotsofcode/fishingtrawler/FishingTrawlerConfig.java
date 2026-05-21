package com.lotsofcode.fishingtrawler;

import java.awt.Color;
import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("fishingTrawlerCompanion")
public interface FishingTrawlerConfig extends Config
{
	@ConfigSection(
		name = "Highlights",
		description = "What to outline on the boat",
		position = 0
	)
	String highlightSection = "highlights";

	@ConfigSection(
		name = "Trip info",
		description = "On-screen trip progress display",
		position = 1
	)
	String tripSection = "trip";

	@ConfigItem(
		keyName = "highlightLeaks",
		name = "Highlight leaks",
		description = "Outline small leaks on the hull (bail with a bucket)",
		section = highlightSection,
		position = 0
	)
	default boolean highlightLeaks()
	{
		return true;
	}

	@ConfigItem(
		keyName = "highlightHoles",
		name = "Highlight holes",
		description = "Outline large hull breaches (plug with a plank)",
		section = highlightSection,
		position = 1
	)
	default boolean highlightHoles()
	{
		return true;
	}

	@Alpha
	@ConfigItem(
		keyName = "leakColor",
		name = "Leak color",
		description = "Outline color for small leaks",
		section = highlightSection,
		position = 2
	)
	default Color leakColor()
	{
		return new Color(255, 200, 0, 200);
	}

	@Alpha
	@ConfigItem(
		keyName = "holeColor",
		name = "Hole color",
		description = "Outline color for large hull holes",
		section = highlightSection,
		position = 3
	)
	default Color holeColor()
	{
		return new Color(255, 60, 60, 220);
	}

	@ConfigItem(
		keyName = "showTripPanel",
		name = "Show trip panel",
		description = "Display elapsed trip time and active leak/hole counts",
		section = tripSection,
		position = 0
	)
	default boolean showTripPanel()
	{
		return true;
	}
}
