package com.ballthrower.communication;

public class Packet
{
	private byte[] _contents;
	private short _position;

	public Packet(byte size)
	{
		_contents = new byte[size];
	}

	public Packet(byte[] contents)
	{
		_contents = contents;
	}

	public boolean canRead()
	{
		return _position < _contents.length;
	}

	public boolean canWrite(byte size)
	{
		return _position + size < _contents.length;
	}

	public void writeByte(byte value)
	{
		_contents[_position++] = value;
	}

	public byte readByte()
	{
		return _contents[_position++];
	}
}