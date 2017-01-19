% Author: Kevin Dalle
% Description: Agent code for the wumpus world problem. This code will allow it to
% pick up gold and move around.
%[Stench,Breeze,Glitter,Bump,Scream,NLHint,Image]

consult(lists).

:- dynamic([agentLoc/2,
		agentDir/1,
		agentPath/2,
		gotGold/1,
		agentTurn/1]).

init_agent :- init_my_agent.

% Here I retract all my dynamic predicates and set them to the desired
% initial value for my agent.

init_my_agent :- 
	retractall(agentLoc(_,_)), 
	retractall(agentDir(_)),
	retractall(agentPath(_,_)),
	retractall(gotGold(_)),
	retractall(safe(_,_)),
	retractall(agentTurn(_)),
	assert(gotgold(no)),
	assert(agentLoc(1,1)),
	assert(agentPath(1,1)), 
	assert(agentDir(0)),
	assert(agentTurn(0)).

% This is the beginning of where decisions will be made
run_agent(Percept,Action) :- 
	my_agent_actions(Percept,Action).

% I will always grab gold if i am in a square with gold, then I assert that
% the agent has gold for future reference./
my_agent_actions([_,_,yes,_,_,_,_],grab) :-
	assert(gotGold(1)).

% If I am at the start and have gold I will leave./
my_agent_actions(_,climb) :- 
	gotGold(X), 
	X =:= 1, 
	agentLoc(Y,Z),
	Y=:=1,
	Z=:=1.

% If i have gold and am not at the start I will pick which way to go and move
% the next few do the same thing for differing angles and location.
my_agent_actions(_,goforward) :-
	gotGold(X),
	X =:= 1,
	agentPath(_,C),
	agentLoc(_,E),
	E < C,
	agentDir(A),
	A =:= 90,
	leave(_).

my_agent_actions(_,goforward) :-
	gotGold(X),
	X =:= 1,
	agentPath(_,C),
	agentLoc(_,E),
	E > C,
	agentDir(A),
	A =:= 270,
	leave(_).

my_agent_actions(_,goforward) :-
	gotGold(X),
	X =:= 1,
	agentPath(C,_),
	agentLoc(E,_),
	E < C,
	agentDir(A),
	A =:= 0,
	leave(_).

my_agent_actions(_,goforward) :-
	gotGold(X),
	X =:= 1,
	agentPath(C,_),
	agentLoc(E,_),
	E > C,
	agentDir(A),
	A =:= 180,
	leave(_).

my_agent_actions(_,goforward) :-
	gotGold(X),
	X =:= 1,
	agentPath(_,C),
	agentLoc(_,E),
	E < C,
	agentDir(A),
	A =:= 90,
	leave(_).

my_agent_actions(_,goforward) :-
	gotGold(X),
	X =:= 1,
	agentPath(_,C),
	agentLoc(_,E),
	E > C,
	agentDir(A),
	A =:= 270,
	leave(_).

my_agent_actions(_,goforward) :-
	gotGold(X),
	X =:= 1,
	agentPath(C,_),
	agentLoc(E,_),
	E < C,
	agentDir(A),
	A =:= 0,
	leave(_).

my_agent_actions(_,goforward) :-
	gotGold(X),
	X =:= 1,
	agentPath(C,_),
	agentLoc(E,_),
	E > C,
	agentDir(A),
	A =:= 180,
	leave(_).

% The following deal with the direction of the agent when it has gold based
% on the location and angle.
my_agent_actions(_,turnleft) :-
	gotGold(X),
	X =:= 1,
	agentPath(_,C),
	agentLoc(_,E),
	E < C,
	agentDir(A),
	A =:= 0,
	(NAngle is (90) mod 360), 
	retractall(agentDir(_)), 
	assert(agentDir(NAngle)).

my_agent_actions(_,turnright) :-
	gotGold(X),
	X =:= 1,
	agentPath(_,C),
	agentLoc(_,E),
	E < C,
	agentDir(A),
	A =:= 180,
	(NAngle is (90) mod 360), 
	retractall(agentDir(_)), 
	assert(agentDir(NAngle)).

my_agent_actions(_,turnright) :-
	gotGold(X),
	X =:= 1,
	agentPath(_,C),
	agentLoc(_,E),
	E < C,
	agentDir(A),
	A =:= 270,
	(NAngle is (180) mod 360), 
	retractall(agentDir(_)), 
	assert(agentDir(NAngle)).

my_agent_actions(_,turnright) :-
	gotGold(X),
	X =:= 1,
	agentPath(_,C),
	agentLoc(_,E),
	E > C,
	agentDir(A),
	A =:= 0,
	(NAngle is (270) mod 360), 
	retractall(agentDir(_)), 
	assert(agentDir(NAngle)).

my_agent_actions(_,turnright) :-
	gotGold(X),
	X =:= 1,
	agentPath(_,C),
	agentLoc(_,E),
	E > C,
	agentDir(A),
	A =:= 90,
	(NAngle is (0) mod 360), 
	retractall(agentDir(_)), 
	assert(agentDir(NAngle)).

my_agent_actions(_,turnleft) :-
	gotGold(X),
	X =:= 1,
	agentPath(_,C),
	agentLoc(_,E),
	E > C,
	agentDir(A),
	A =:= 180,
	(NAngle is (270) mod 360), 
	retractall(agentDir(_)), 
	assert(agentDir(NAngle)).

my_agent_actions(_,turnright) :-
	gotGold(X),
	X =:= 1,
	agentPath(C,_),
	agentLoc(E,_),
	E < C,
	agentDir(A),
	A =:= 180,
	(NAngle is (90) mod 360), 
	retractall(agentDir(_)), 
	assert(agentDir(NAngle)).

my_agent_actions(_,turnright) :-
	gotGold(X),
	X =:= 1,
	agentPath(C,_),
	agentLoc(E,_),
	E < C,
	agentDir(A),
	A =:= 90,
	(NAngle is (0) mod 360), 
	retractall(agentDir(_)), 
	assert(agentDir(NAngle)).

my_agent_actions(_,turnleft) :-
	gotGold(X),
	X =:= 1,
	agentPath(C,_),
	agentLoc(E,_),
	E < C,
	agentDir(A),
	A =:= 270,
	(NAngle is (0) mod 360), 
	retractall(agentDir(_)), 
	assert(agentDir(NAngle)).

my_agent_actions(_,turnright) :-
	gotGold(X),
	X =:= 1,
	agentPath(C,_),
	agentLoc(E,_),
	E > C,
	agentDir(A),
	A =:= 0,
	(NAngle is (270) mod 360), 
	retractall(agentDir(_)), 
	assert(agentDir(NAngle)).

my_agent_actions(_,turnleft) :-
	gotGold(X),
	X =:= 1,
	agentPath(C,_),
	agentLoc(E,_),
	E > C,
	agentDir(A),
	A =:= 90,
	(NAngle is (180) mod 360), 
	retractall(agentDir(_)), 
	assert(agentDir(NAngle)).

my_agent_actions(_,turnright) :-
	gotGold(X),
	X =:= 1,
	agentPath(C,_),
	agentLoc(E,_),
	E > C,
	agentDir(A),
	A =:= 270,
	(NAngle is (180) mod 360), 
	retractall(agentDir(_)), 
	assert(agentDir(NAngle)).

% These are dealing with if i am about to run into a wall. To solve this
my_agent_actions(_,turnright) :-
	agentLoc(X,Y),
	X =:= 1,
	Y =:= 1,
	agentDir(A),
	A =:= 180,
	retractall(agentDir(_)),
	assert(agentDir(90)).

my_agent_actions(_,turnleft) :-
	agentLoc(X,Y),
	X =:= 1,
	Y =:= 1,
	agentDir(A),
	A =:= 270,
	retractall(agentDir(_)),
	assert(agentDir(0)).

my_agent_actions(_,turnleft) :-
	agentLoc(X,Y),
	X =:= 8,
	Y =:= 1,
	agentDir(A),
	A =:= 0,
	retractall(agentDir(_)),
	assert(agentDir(90)).

my_agent_actions(_,turnleft) :-
	agentLoc(X,Y),
	X =:= 8,
	Y =:= 8,
	agentDir(A),
	A =:= 90,
	retractall(agentDir(_)),
	assert(agentDir(180)).

my_agent_actions(_,turnleft) :-
	agentLoc(X,Y),
	X =:= 1,
	Y =:= 8,
	retractall(agentDir(_)),
	assert(agentDir(270)).

% These tell the agent to go forward after correcting for almost running into
% a wall

my_agent_actions(_,goforward) :-
	agentLoc(X,Y),
	X =:= 8,
	Y =:= 1,
	agentDir(A),
	A =:= 90,
	movelocation(_).

my_agent_actions(_,goforward) :-
	agentLoc(X,Y),
	X =:= 8,
	Y =:= 8,
	agentDir(A),
	A =:= 180,
	movelocation(_).

my_agent_actions(_,goforward) :-
	agentLoc(X,Y),
	X =:= 1,
	Y =:= 8,
	agentDir(A),
	A =:= 270,
	movelocation(_).

my_agent_actions(_,goforward) :-
	agentLoc(X,Y),
	X =:= 8,
	Y =:= 1,
	agentDir(A),
	A =:= 90,
	movelocation(_).

my_agent_actions(_,goforward) :-
	agentLoc(X,Y),
	X =:= 8,
	Y =:= 8,
	agentDir(A),
	A =:= 180,
	movelocation(_).

my_agent_actions(_,goforward) :-
	agentLoc(X,Y),
	X =:= 1,
	Y =:= 8,
	agentDir(A),
	A =:= 270,
	movelocation(_).

my_agent_actions([_,_,no,_,_,_,_], goforward) :- 
	movelocation(_).

% updates teh location of the agent internally/
movelocation(_) :- 
	agentDir(A),
	agentLoc(X,Y),
	changeLoc(X,Y,A,X1,Y1),
	retractall(agentLoc(X,Y)),
	assert(agentLoc(X1,Y1)),
	asserta(agentPath(X,Y)).

% This is for when leaving the cave/
leave(_) :-
	agentDir(A),
	agentLoc(X,Y),
	changeLoc(X,Y,A,X1,Y1),
	retractall(agentLoc(X,Y)),
	assert(agentLoc(X1,Y1)).

% These actually change the values for the new location based on the angle
% given
changeLoc(X,Y,0,X1,Y) :-
	X1 is X+1.

changeLoc(X,Y,90,X,Y1) :-
	Y1 is Y+1.

changeLoc(X,Y,180,X1,Y) :-
	X1 is X-1.

changeLoc(X,Y,270,X,Y1) :-
	Y1 is Y-1.
	
