cc_library (
    name = "Box2D",
    hdrs = ["Box2D.h",],
    deps = [
        ":Box2Dlib",
    ],
    visibility = ["//visibility:public"],
)


cc_library (
    name = "Box2Dlib",
    hdrs = glob([
        "Box2D/**/*.h", 
    ]),
    srcs = glob([
	"Box2D/**/*.cpp", 
    ]),
)
