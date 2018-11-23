package net.teamfps.savage;

/**
 * 
 * @author Zekye
 *
 */
//AxisAlignedBB
public class AABB {
	public final double minX;
	public final double minY;
	public final double minZ;
	public final double maxX;
	public final double maxY;
	public final double maxZ;

	public AABB(double x1, double y1, double z1, double x2, double y2, double z2) {
		this.minX = Math.min(x1, x2);
		this.minY = Math.min(y1, y2);
		this.minZ = Math.min(z1, z2);
		this.maxX = Math.max(x1, x2);
		this.maxY = Math.max(y1, y2);
		this.maxZ = Math.max(z1, z2);
	}

	public AABB(float x, float y, float z) {
		this((double) x, (double) y, (double) z, (double) (x + 1), (double) (y + 1), (double) (z + 1));
	}

	public AABB setMaxY(double y2) {
		return new AABB(this.minX, this.minY, this.minZ, this.maxX, y2, this.maxZ);
	}

	public boolean equals(Object p_equals_1_) {
		if (this == p_equals_1_) {
			return true;
		} else if (!(p_equals_1_ instanceof AABB)) {
			return false;
		} else {
			AABB axisalignedbb = (AABB) p_equals_1_;
			return Double.compare(axisalignedbb.minX, this.minX) != 0 ? false : (Double.compare(axisalignedbb.minY, this.minY) != 0 ? false : (Double.compare(axisalignedbb.minZ, this.minZ) != 0 ? false : (Double.compare(axisalignedbb.maxX, this.maxX) != 0 ? false : (Double.compare(axisalignedbb.maxY, this.maxY) != 0 ? false : Double.compare(axisalignedbb.maxZ, this.maxZ) == 0))));
		}
	}

	public int hashCode() {
		long i = Double.doubleToLongBits(this.minX);
		int j = (int) (i ^ i >>> 32);
		i = Double.doubleToLongBits(this.minY);
		j = 31 * j + (int) (i ^ i >>> 32);
		i = Double.doubleToLongBits(this.minZ);
		j = 31 * j + (int) (i ^ i >>> 32);
		i = Double.doubleToLongBits(this.maxX);
		j = 31 * j + (int) (i ^ i >>> 32);
		i = Double.doubleToLongBits(this.maxY);
		j = 31 * j + (int) (i ^ i >>> 32);
		i = Double.doubleToLongBits(this.maxZ);
		j = 31 * j + (int) (i ^ i >>> 32);
		return j;
	}

	public AABB addCoord(double x, double y, double z) {
		double d0 = this.minX;
		double d1 = this.minY;
		double d2 = this.minZ;
		double d3 = this.maxX;
		double d4 = this.maxY;
		double d5 = this.maxZ;

		if (x < 0.0D) {
			d0 += x;
		} else if (x > 0.0D) {
			d3 += x;
		}

		if (y < 0.0D) {
			d1 += y;
		} else if (y > 0.0D) {
			d4 += y;
		}

		if (z < 0.0D) {
			d2 += z;
		} else if (z > 0.0D) {
			d5 += z;
		}

		return new AABB(d0, d1, d2, d3, d4, d5);
	}

	public AABB expand(double x, double y, double z) {
		double d0 = this.minX - x;
		double d1 = this.minY - y;
		double d2 = this.minZ - z;
		double d3 = this.maxX + x;
		double d4 = this.maxY + y;
		double d5 = this.maxZ + z;
		return new AABB(d0, d1, d2, d3, d4, d5);
	}

	public AABB expandXyz(double value) {
		return this.expand(value, value, value);
	}

	public AABB union(AABB other) {
		double d0 = Math.min(this.minX, other.minX);
		double d1 = Math.min(this.minY, other.minY);
		double d2 = Math.min(this.minZ, other.minZ);
		double d3 = Math.max(this.maxX, other.maxX);
		double d4 = Math.max(this.maxY, other.maxY);
		double d5 = Math.max(this.maxZ, other.maxZ);
		return new AABB(d0, d1, d2, d3, d4, d5);
	}

	/**
	 * Offsets the current bounding box by the specified amount.
	 */
	public AABB offset(double x, double y, double z) {
		return new AABB(this.minX + x, this.minY + y, this.minZ + z, this.maxX + x, this.maxY + y, this.maxZ + z);
	}

	public double calculateXOffset(AABB other, double offsetX) {
		if (other.maxY > this.minY && other.minY < this.maxY && other.maxZ > this.minZ && other.minZ < this.maxZ) {
			if (offsetX > 0.0D && other.maxX <= this.minX) {
				double d1 = this.minX - other.maxX;

				if (d1 < offsetX) {
					offsetX = d1;
				}
			} else if (offsetX < 0.0D && other.minX >= this.maxX) {
				double d0 = this.maxX - other.minX;

				if (d0 > offsetX) {
					offsetX = d0;
				}
			}

			return offsetX;
		} else {
			return offsetX;
		}
	}

	public double calculateYOffset(AABB other, double offsetY) {
		if (other.maxX > this.minX && other.minX < this.maxX && other.maxZ > this.minZ && other.minZ < this.maxZ) {
			if (offsetY > 0.0D && other.maxY <= this.minY) {
				double d1 = this.minY - other.maxY;

				if (d1 < offsetY) {
					offsetY = d1;
				}
			} else if (offsetY < 0.0D && other.minY >= this.maxY) {
				double d0 = this.maxY - other.minY;

				if (d0 > offsetY) {
					offsetY = d0;
				}
			}

			return offsetY;
		} else {
			return offsetY;
		}
	}

	public double calculateZOffset(AABB other, double offsetZ) {
		if (other.maxX > this.minX && other.minX < this.maxX && other.maxY > this.minY && other.minY < this.maxY) {
			if (offsetZ > 0.0D && other.maxZ <= this.minZ) {
				double d1 = this.minZ - other.maxZ;

				if (d1 < offsetZ) {
					offsetZ = d1;
				}
			} else if (offsetZ < 0.0D && other.minZ >= this.maxZ) {
				double d0 = this.maxZ - other.minZ;

				if (d0 > offsetZ) {
					offsetZ = d0;
				}
			}

			return offsetZ;
		} else {
			return offsetZ;
		}
	}

	public boolean intersectsWith(AABB other) {
		return this.intersects(other.minX, other.minY, other.minZ, other.maxX, other.maxY, other.maxZ);
	}

	public boolean intersects(double x1, double y1, double z1, double x2, double y2, double z2) {
		return this.minX < x2 && this.maxX > x1 && this.minY < y2 && this.maxY > y1 && this.minZ < z2 && this.maxZ > z1;
	}

	public double getAverageEdgeLength() {
		double d0 = this.maxX - this.minX;
		double d1 = this.maxY - this.minY;
		double d2 = this.maxZ - this.minZ;
		return (d0 + d1 + d2) / 3.0D;
	}

	public AABB contract(double value) {
		return this.expandXyz(-value);
	}

	public boolean hasNaN() {
		return Double.isNaN(this.minX) || Double.isNaN(this.minY) || Double.isNaN(this.minZ) || Double.isNaN(this.maxX) || Double.isNaN(this.maxY) || Double.isNaN(this.maxZ);
	}

	public String toString() {
		return "box[min(" + this.minX + ", " + this.minY + ", " + this.minZ + "), max(" + this.maxX + ", " + this.maxY + ", " + this.maxZ + ")]";
	}

}
