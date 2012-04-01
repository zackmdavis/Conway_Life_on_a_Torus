from time import sleep

N = 9

def empty_universe():
    return [[' ' for i in range(N)] for j in range(N)]

def neighborhood(i, j, board):
    live_neighbors = 0
    for x_offset in [-1, 0, 1]:
        for y_offset in [-1, 0, 1]:
            if board[(i+x_offset)%N][(j+y_offset)%N]=='X':
                if (x_offset, y_offset)!=(0,0):
                    live_neighbors += 1
    return live_neighbors
              

def advance_generation(board):
    new_board = empty_universe()
    for i in range(N):
        for j in range(N):
            if board[i][j]=='X':
                if neighborhood(i, j, board)<2:
                    new_board[i][j] = ' '
                elif neighborhood(i, j, board) in [2, 3]:
                    new_board[i][j] = 'X'
                elif neighborhood(i, j, board)>3:
                    new_board[i][j] = ' '
            if board[i][j]==' ':
                if neighborhood(i, j, board) == 3:
                    new_board[i][j] = 'X'
                else:
                    new_board[i][j] = ' '
    return new_board

def display_universe(board):
    for row in board:
        print(row)

def test_universe(lifetime):
    universe = empty_universe()
    universe[0][1] ='X'
    universe[1][2] ='X'
    universe[2][0] ='X'
    universe[2][1] ='X'
    universe[2][2] ='X'
    for i in range(lifetime):
        display_universe(universe)
        print("GENERATION", i)
        universe = advance_generation(universe)
        sleep(0.1)

test_universe(1000)
