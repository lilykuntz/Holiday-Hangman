# -*- coding: utf-8 -*-
"""
Created on Wed Dec  7 09:24:08 2016

@author: lilykuntz
"""

"""
Assignment 10: Breakout
Lillian Kuntz
December 9, 2016
"""


"""
Cool Stuff:
- when the ball hits the bottom it says "Game Over"
- when all bricks are hit the game stops and it says "WINNER!"
-when you lose you can click to restart the game

"""
import pyprocessing as g
import random

WIDTH = 600
HEIGHT = 600    

class Ball:
    def __init__(self, x, y, radius, color):
        self.x = x
        self.y = y
        self.radius = radius
        self.color = color
        self.vx = 8
        self.vy = -8
        self.radius= 10
        
    def draw(self):
        g.fill(255, 255, 255)
        g.ellipseMode(g.CENTER)
        g.ellipse(self.x,self.y,self.radius*2,self.radius*2)
    
    def update(self, obstacles): 
        self.x = self.x + self.vx
        self.y = self.y + self.vy
        if self.y - self.radius <= 0 :
            self.vy = -self.vy
        if self.x + self.radius >= WIDTH or self.x - self.radius <= 0:
            self.vx =-self.vx
        
        hits=[]
        for i in obstacles:  
            hits+=(i.bounces(self))
            if 't' in hits or 'b' in hits:
                self.vy*= -1
            if 'r' in hits or 'l' in hits:
                self.vx*= -1    
            if ('l' in hits and 'b' in hits) or ('l' in hits and 't' in hits)or ('r' in hits and 'b' in hits) or ('r' in hits and 't' in hits):
                self.vx*= -1
           
class Paddle:
    def __init__(self, width, height, color):
        self.width = width
        self.height = height
        self.color = color 
        self.x= g.mouse.x
        self.y= HEIGHT- 20
        self.width= 150
        self.height=20
        
    def draw(self):
        g.fill(34,139 , 34)
        g.rect(self.x, self.y, self.width, self.height) 
    
    def update(self):
        self.x= g.mouse.x
    
    def bounces(self, ball):
        if ball.x + ball.radius >= self.x and ball.x - ball.radius <= self.x + self.width and ball.y + ball.radius >= self.y:
            return ['t']
        else:
            return []
        
class Brick:
    def __init__(self, x, y, width, height, color):
        self.x = x
        self.y = y        
        self.width = width
        self.height = height
        self.color = color 
        self.visible= True
        self.width= 100
        self.height= 30
        
    def draw(self):
        if self.visible:
            g.fill(255, 0, 0)
            g.rect(self.x, self.y, self.width, self.height) 
    
    def bounces(self, ball): 
       
        if self.visible:
            
            if ball.x + ball.radius >= self.x and ball.x - ball.radius<= self.x + self.width and  ball.y- ball.radius<= self.y+self.height < ball.y - ball.vy - ball.radius : 
                self.visible= False
                return ['b']
            elif ball.x + ball.radius >= self.x and ball.x - ball.radius<= self.x + self.width  and ball.y+ ball.radius>= self.y> ball.y - ball.vy +ball.radius : 
                self.visible= False
                return ['t']
            elif ball.x + ball.radius >= self.x>= ball.x + ball.radius - ball.vx and ball.y - ball.radius <= self.y + self.height and ball.y + ball.radius >= self.y:
                self.visible= False
                return ['l']
            elif ball.x - ball.radius <= self.x + self.width<= ball.x-ball.radius - ball.vx and ball.y - ball.radius <= self.y + self.height and ball.y + ball.radius >= self.y:
                self.visible= False
                return ['r']
            else: 
                return []
            
        else:
            return []
      
            

ball = Ball(WIDTH//2, HEIGHT-50, 10,(255, 255, 255))
ball2= Ball(WIDTH//3, HEIGHT-20, 5,(255, 255, 255))
paddle = Paddle(150, 20 ,(34,139 , 34))
brick = Brick(-100, 50, 100, 30,(255, 0, 0))
brick2=Brick(-100, 100, 100, 30, (0, 255, 0))
brick3=Brick(-100, 150, 100, 30, (0, 255, 0))
obstacles=[]
bricks= []
def setup():
    g.size(WIDTH, HEIGHT)
    obstacles.append(paddle)
    brick = Brick(-100, 50, 100, 30,(255, 0, 0))

    for i in range(6):
        brick = Brick(brick.x,  50, 100, 30,(255, 0, 0))
        obstacles.append(brick)
        bricks.append(brick)
        brick.x=brick.x+100
    brick2=Brick(-100, 100, 100, 30, (0, 255, 0))
    for i in range(6):
        brick2=Brick(brick2.x, 100, 100, 30, (0, 255, 0))
        obstacles.append(brick2)
        bricks.append(brick2)
        brick2.x=brick2.x+100
    brick3=Brick(-100, 150, 100, 30, (0, 255, 0))
    for i in range(6):
        brick3=Brick(brick3.x, 150, 100, 30, (0, 255, 0))
        obstacles.append(brick3)
        bricks.append(brick3)
        brick3.x=brick3.x+100
    
def draw():
    g.background(240,255,255)  
    paddle.update()
    for i in obstacles:
        i.draw()
    ball.draw()
    ball.update(obstacles)   
    if ball.y - ball.radius >= HEIGHT:
        g.textSize(50);
        g.fill(0,0,0)
        g.text("Game Over", WIDTH//4, HEIGHT//2);
        g.textSize(30);
        g.fill(0,0,0)
        g.text("Click for New Game", WIDTH//4, HEIGHT-220);
        g.noLoop()
        
    invisible= 0
    for i in bricks:
        if i.visible:
            pass
        else:
            invisible+=1
    if invisible==18:
        g.textSize(50);
        g.fill(0,0,0)
        g.text("WINNER!", WIDTH//4, HEIGHT//2)
        g.noLoop
        
    
def mouseClicked():
    #invisisble=0
    #for i in bricks:
     #   if i.visible:
      #      pass
       # else:
        #    invisible+=1
    if ball.y - ball.radius >= HEIGHT :#or invisible==18:
        ball.x= WIDTH//2
        ball.y= HEIGHT-50
        ball.vy= -8
        ball.vx= 8
        for i in obstacles:
            i.visible =True   
       
    g.loop()         

g.mouseClicked= mouseClicked
g.setup = setup
g.draw = draw

g.run()