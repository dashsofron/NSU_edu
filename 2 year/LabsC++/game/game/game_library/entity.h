#pragma once
#include "../obj.h"
#include <stdlib.h>
#include <iostream> 

struct Tile {
	std::string texture;
	Tile(const Tile&) = default;
};


class entity
{
protected:
	float x_;
	float y_;
	int text_w;
	int text_h;
public:
	bool drawy = 1;

	entity(float x = 0, float y = 0) {
		x_ = x;
		y_ = y;
		text_w = 0;
		text_h = 0;
	}
	virtual ~entity()=default;
	void set_place(float x, float y) {
		x_ = x;
		y_ = y;
	}

	void set_text(Tile name) {
		text_w = TextureManager::getInstance()->get(name.texture).getWidth();
		text_h = TextureManager::getInstance()->get(name.texture).getHeight();
	}

	void set_text(int w, int h) {
		text_w = w;
		text_h = h;
	}

	float get_left() {
		return(x_ - text_w / 2);
	}
	float get_right() {
		return(x_ + text_w / 2);

	}float get_top() {
		return(y_ - text_h / 2);
	}float get_bot() {
		return(y_ + text_h / 2);

	}
	float x() {
		return x_;
	}

	float y() {
		return y_;
	}

};

class move_obj:public entity{
private:
	float vx_;
	float vy_;
public:
	move_obj(float x = 0, float y = 0, float vx = 0, float vy = 0) {
		x_ = x;
		y_ = y;
		vx_ = vx;
		vy_ = vy;
	}
	void set_velocity(float vx, float vy) {
		vx_ = vx;
		vy_ = vy;
	}
	virtual void check_collision(int edge_w,int edge_h) {
		if ((get_left() <= edge_w) && (vx() < 0.0) || (get_right() >= edge_w * 9) && (vx() > 0))set_velocity(0.0,vy());
		if ((get_top() <= edge_h) && (vy() < 0.0) || (get_bot() >= edge_h * 9) && (vy() > 0)) set_velocity(vx(), 0.0);
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
	~move_obj() {};
};

class BLOCK :public entity {
	public:
		BLOCK(float x = 0, float y = 0) {
		x_ = x;
		y_ = y;
		text_w = 0;
		text_h = 0;
	}
};

class BANANA :public entity {
public:
	BANANA(float x = 0, float y = 0) {
		x_ = x;
		y_ = y;
		text_w = 0;
		text_h = 0;
	}
};




class ghost :public move_obj, public Obj {

public:
	virtual void check_collision(int edge_w, int edge_h)override {
		if ((get_left() <= edge_w) && (vx() < 0.0) || (get_right() >= edge_w * 9) && (vx() > 0))set_velocity(-vx(), vy());
		if ((get_top() <= edge_h) && (vy() < 0.0) || (get_bot() >= edge_h * 9) && (vy() > 0)) set_velocity(vx(), -vy());
	}
	void check_collision(BLOCK& B) {
		//вправо
		if ((get_right() + 1 >= B.get_left()) && (get_right() - 1 <= B.get_right()) && (vx() > 0)
			&& ((get_top() >= B.get_top()) && (get_top() <= B.get_bot())
				|| (get_bot() >= B.get_top()) && (get_bot() <= B.get_bot())))
			set_velocity(-vx(), vy());
		//влево
		if ((get_left() + 1 >= B.get_left()) && (get_left() - 1 <= B.get_right()) && (vx() < 0)
			&& ((get_top() >= B.get_top()) && (get_top() <= B.get_bot())
				|| (get_bot() >= B.get_top()) && (get_bot() <= B.get_bot())))
			set_velocity(-vx(), vy());

		//вверх
		if ((get_top() + 1 >= B.get_top()) && (get_top() - 1 <= B.get_bot()) && (vy() < 0)
			&& ((get_right() <= B.get_right()) && (get_right() >= B.get_left())
				|| (get_left() <= B.get_right()) && (get_left() >= B.get_left())))
			set_velocity(vx(),-vy());

		//вниз
		if ((get_bot() + 1 >= B.get_top()) && (get_bot() - 1 <= B.get_bot()) && (vy() > 0)
			&& ((get_right() <= B.get_right()) && (get_right() >= B.get_left())
				|| (get_left() <= B.get_right()) && (get_left() >= B.get_left())))
			set_velocity(vx(), -vy());
	}
	//void move(float time) {
	//	x_ += time * vx_;
	//	y_ += time * vy_;
	//}
};

class player :public move_obj,public Obj {
private :
		bool alive = 1;
		int score = 0;
public:
	using move_obj::check_collision;
	void check_collision(BLOCK &B) {


			if ((get_right() + 1 >= B.get_left()) && (get_right() - 1 <= B.get_right()) && (vx() > 0)
				&& ((get_top() >= B.get_top()) && (get_top() <= B.get_bot())
					|| (get_bot() >= B.get_top()) && (get_bot() <= B.get_bot())))
				set_velocity(0.0, vy());
			//влево
			if ((get_left() + 1 >= B.get_left()) && (get_left() - 1 <= B.get_right()) && (vx() < 0)
				&& ((get_top() >= B.get_top()) && (get_top() <= B.get_bot())
					|| (get_bot() >= B.get_top()) && (get_bot() <= B.get_bot())))
				set_velocity(0.0, vy());

			//вверх
			if ((get_top() + 1 >= B.get_top()) && (get_top() - 1 <= B.get_bot()) && (vy() < 0)
				&& ((get_right() <= B.get_right()) && (get_right() >= B.get_left())
					|| (get_left() <= B.get_right()) && (get_left() >= B.get_left())))
				set_velocity(vx(), 0.0);

			//вниз
			if ((get_bot() + 1 >= B.get_top()) && (get_bot() - 1 <= B.get_bot()) && (vy() > 0)
				&& ((get_right() <= B.get_right()) && (get_right() >= B.get_left())
					|| (get_left() <= B.get_right()) && (get_left() >= B.get_left())))
				set_velocity(vx(), 0.0);

	}
	void get_banana() {
		score++;
	}
	void check_collision(BANANA& B) {
		if (((get_right() - 2 > B.get_left()) && (get_right() + 2 < B.get_right())
			|| (get_left() - 2 > B.get_left()) && (get_left() + 2 < B.get_right()))
			&& ((get_top() - 2 > B.get_top()) && (get_top() + 2 < B.get_bot())
				|| (get_bot() - 2 > B.get_top()) && (get_bot() + 2 < B.get_bot())))
		{
			get_banana(); B.drawy = 0;
		}
	}
	bool dead() {
		return !alive;
	}
	void check_collision(ghost& G) {
		bool is_left_inside = (get_left() >= G.get_left()) && (get_left() <= G.get_right());
		bool is_right_inside = (get_right() >= G.get_left()) && (get_right() <= G.get_right());
		bool is_down_inside = (get_bot() >= G.get_top()) && (get_bot() <= G.get_bot());
		bool is_up_inside = (get_top() >= G.get_top()) && (get_top() <= G.get_bot());

		if (((get_right()>= G.get_left()) && (get_right() <= G.get_right())
			|| (get_left() >= G.get_left()) && (get_left()<= G.get_right()))
			&& ((get_top() >= G.get_top()) && (get_top() <= G.get_bot())

				|| (get_bot() >= G.get_top()) && (get_bot() <= G.get_bot())))
		{
			alive = 0;
			std::cout << "dead";
		}

	}

};



