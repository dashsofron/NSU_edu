#include <vector> 
#include "window.h"

class Command {
public:
    virtual ~Command() = default;
    virtual void execute(std::vector<int> &container, std::vector<Color> &colors) const = 0;
};
