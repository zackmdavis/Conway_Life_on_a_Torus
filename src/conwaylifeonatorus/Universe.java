package conwaylifeonatorus;

interface Universe
{
    public int getRows();
    public int getCols();
    public State getCellState(int i, int j);
    public void setCellState(int i, int j, State s);
    public void advanceGeneration();
    public int queryPopulation();
}