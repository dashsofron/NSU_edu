#pragma once
class entity
{
private:
	float x_;
	float y_;
	float vx_;
	float vy_;
public:
	entity(float x = 0, float y = 0, float vx = 0, float vy = 0) {
		x_ = x;
		y_ = y;
		vx_ = vx;
		vy_ = vy;
	}
	void set_velocity(float vx, float vy) {
		vx_ = vx;
		vy_ = vy;
	}
	void set_place(float x, float y) {
		x_ = x;
		y_ = y;
	}
	float x() {
		return x_;
	}

	float y() {
		return y_;
	}
	float vx() {
		return vx_;
	}
	float vy() {
		return vy_;
	}
	void move(float time) {
		x_ += time * vx_;
		y_ += time * vy_;
	}

};

class player :public entity {
private :
		int life = 1;
		int score = 0;
};

