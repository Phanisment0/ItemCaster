package io.phanisment.itemcaster.parser;

public final class ProgressBarParse {
	private static final int TOTAL_BARS = 100;
	private static final char BAR_FONT = '|';

	private float current;
	private float max;

	public String progress_bar;

	public ProgressBarParse(float current, float max) {
		this.current = current;
		this.max = max;
	}

	public String parse() {
		if (current <= 0) return "";
		float progress = current / max;
		int filled = Math.round(progress * TOTAL_BARS);

		var builder = new StringBuilder("<gray>[</gray>");
		for (int i = 0; i < TOTAL_BARS; i++) {
			if (i < filled) builder.append("<color:#69DFFF>" + BAR_FONT + "</color>");
			else builder.append("<dark_gray>" + BAR_FONT + "</dark_gray>");
		}
		builder.append("<gray>]</gray>");
		return builder.toString();
	}

	public String formatedTime() {
		if (current >= 60) {
			int minutes = (int) (current / 60);
			int secs = (int) (current % 60);
			return String.format("%dm %ds", minutes, secs);
		} else if (current >= 1) {
			return String.format("%.1fs", current);
		} else {
			return String.format("%dms", (int)(current * 1000));
		}
	}
}
