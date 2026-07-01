package io.phanisment.itemcaster.parser;

public final class ProgressBarParse {
	public static int TOTAL_BARS = 20;
	private static final char BAR_FONT = '|';

	private double current;
	private double max;

	public String progress_bar;

	public ProgressBarParse(double current, double max) {
		this.current = current;
		this.max = max;
	}

	public String parse() {
		if (current <= 0) return "";
		double progress = current / max;
		long filled = Math.round(progress * TOTAL_BARS);

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
