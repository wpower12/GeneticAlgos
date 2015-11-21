# GeneticAlgos
Work done while reading "An Introduction to Genetic Algorithms for Scientists and Engineers, David Coley". 

http://www.amazon.com/Introduction-Genetic-Algorithms-Scientists-Engineers/dp/9810236026

So far this contains a Java implementation of the Little Genetic Algorithm.  The LGA is a simplified exploration of the 
general methods of genetic algorithms.  The LGA proceeds by creating an intial population, and then for a set number of
generations, evaluating the individuals against a fitness function, selecting individuals for 'reproduction', then 
generating a new population from these selected individuals.  The process repeats, creating and evaluating succesive
generations.  If properly tuned, the LGA can converge (but is far from gaurenteed) to an individual which represents a 
maximum fitness.  

In this implementation, the selection phase is done by combining a random weighting of fitness values wiht a sort.
By changing the direction of the sort, we change from maximizing to minizing the fitness value.  Currently the 
comparison operator is such that the fitness value is minimized.  
