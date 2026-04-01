package io.phanisment.itemcaster.adapter;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import io.phanisment.itemcaster.util.Identifier;

public class IdentiferAdapter extends TypeAdapter<Identifier> {
	@Override
	public void write(JsonWriter out, Identifier value) throws IOException {
		if (value == null) out.nullValue();
		else out.value(value.toString());
	}

	@Override
	public Identifier read(JsonReader in) throws IOException {
		if (in.peek() != JsonToken.NULL) return new Identifier(in.nextString());
		in.nextNull();
		return null;
	}
}
