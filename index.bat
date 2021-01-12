@echo off
echo NUL>_.class&&del /s /f /q *.class
cls
javac -cp com/krzem/quadratic_ray_sphere_collision/modules/jogl-all-natives-windows-amd64.jar;com/krzem/quadratic_ray_sphere_collision/modules/jogl-all.jar;com/krzem/quadratic_ray_sphere_collision/modules/gluegen-rt-natives-windows-amd64.jar;com/krzem/quadratic_ray_sphere_collision/modules/gluegen-rt.jar; com/krzem/quadratic_ray_sphere_collision/Main.java&&java -cp com/krzem/quadratic_ray_sphere_collision/modules/jogl-all-natives-windows-amd64.jar;com/krzem/quadratic_ray_sphere_collision/modules/jogl-all.jar;com/krzem/quadratic_ray_sphere_collision/modules/gluegen-rt-natives-windows-amd64.jar;com/krzem/quadratic_ray_sphere_collision/modules/gluegen-rt.jar; com/krzem/quadratic_ray_sphere_collision/Main
start /min cmd /c "echo NUL>_.class&&del /s /f /q *.class"
