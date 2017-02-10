#!/usr/bin/env python2
# -*- coding: utf-8 -*-
"""
Created on Mon Feb  6 11:46:09 2017

@author: c3ll1an
"""
import random
import numpy as np

class Network(object):
    
    def __init__(self, sizes):
        self.num_layers = len(sizes) #Sizes contains the number of neurons in the respective layers
                                     #The len of the sizes array is the number of layers in the network
        self.sizes = sizes #Stores the sizes array to be able to access the number of neurons in each
                            #layer later.
        #The initial biases are initialized randomly.
        #[:-1] returns all elements except the last one
        #[1:] returns all the elements but the first one
        self.biases = [np.random.randn(y,1) for y in sizes[1:]]
        self.weights = [np.random.randn(y,x)
                        for x,y in zip(sizes[:-1], sizes[1:])] 
        #The above creates weights for every layer. Starts with the first layer in the x position
        #(which excludes the last since it has no outgoing weights) and what they go to in the y
        #position (excluding the input layer since no weights go to them). This is then put into an
        #array where it is an array of numpy arrays (which contains more normal arrays) each inner
        #corresponds to a layer. The array's inside that correspond to the weights from the previous
        #layer to each node in the current layer.
        #The biases array is similar and contains a bias for each node in each layer.
        
    def sigmoid(self,z):
        """This is the sigmoid function of the network which is our activation function"""
        return 1.0/(1.0+np.exp(-z))
    
    def feedforward(self, a):
        """Return the output of the network if "a" is an input."""
        for b, w in zip(self.biases, self.weights):
            """a' = sigmoid(w*a + b)"""
            a = self.sigmoid(np.dot(w,a)+b) 
        return a
    
    def SGD(self, training_data, epochs, mini_batch_size, eta, test_data=None):
        """Train the neural network using mini-batch stochastic gradient descent.
        The "training_data" is a list of tuples "(x,y)" representing the training
        and the desired outputs. If "test_data" is provided the the network will
        be evaluated against the test data after each epoch, and partial progress
        printed out. This is useful for tracking progress but slows things down
        substantially."""
        if test_data: n_test = len(test_data)
        n = len(training_data)
        for j in xrange(epochs):
            random.shuffle(training_data)
            mini_batches = [
                    training_data[k:k+mini_batch_size]
                    for k in xrange(0,n,mini_batch_size)]
            for mini_batch in mini_batches:
                self.update_mini_batch(mini_batch,eta)
            if test_data:
                print "Epoch {0}: {1}/{2}".format(j, self.evaluate(test_data),
                             n_test)
            else:
                    print "Epoch {0} complete".format(j)
                    
    def update_mini_batch(self,mini_batch,eta):
        """Update the network's weights and biases by applying gradient descent 
        using backpropogation to a single mini batch. The "mini_batch" is a list
        of tuples "(x,y)", and "eta" is the learning rate."""
        nabla_b = [np.zeros(b.shape) for b in self.biases]
        nabla_w = [np.zeros(w.shape) for w in self.weights]
        for x,y in mini_batch:
            delta_nabla_b, delta_nabla_w = self.backprop(x,y)
            nabla_b = [nb+dnb for nb,dnb in zip(nabla_b,delta_nabla_b)]
            nabla_w = [nw+dnw for nw,dnw in zip(nabla_w,delta_nabla_w)]
        self.weights = [w-(eta/len(mini_batch))*nw
                        for w,nw in zip(self.weights, nabla_w)]
        self.biases = [b-(eta/len(mini_batch))*nb
                       for b,nb in zip(self.biases, nabla_b)]
        
    def backprop(self,x,y):
        """Return a tuple ''(nabla_b, nabla_w)'' representing the gradient for
        the cost function C_x. ''nabla_b'' and ''nabla_w'' are layer-by-layer
        lists of numpy arrays, similar to ''self.biases'' and ''self.weights''."""
        nabla_b = [np.zeros(b.shape) for b in self.biases]
        nabla_w = [np.zeros(w.shape) for w in self.weights]
        # feedforward
        activation = x
        activations = [x] #list to store all the activations, layer by layer
        zs = [] #List to store all the z vectors, layer by layer
        for b,w in zip(self.biases,self.weights):
            z = np.dot(w,activation)+b #Weighted input
            zs.append(z)
            activation = self.sigmoid(z)
            activations.append(activation)
        #backward pass
        delta = self.cost_derivative(activations[-1],y) * \
                                    self.sigmoid_prime(zs[-1])
        nabla_b[-1] = delta
        nabla_w[-1] = np.dot(delta,activations[-2].transpose())
        #Not that the variable l in the loop below is used a little differently
        #to the notation in chapter 2 of the book. Here, l=1 means the last layer
        #of neurons, l=2 is the second-last layer, and so on. It's a renumbering
        #of the scheme in the book, used here to take advantage of the fact that
        #python can use negative indices in lists.
        for l in xrange(2,self.num_layers): 
            z = zs[-l]
            sp = self.sigmoid_prime(z)
            delta = np.dot(self.weights[-l+1].transpose(),delta)*sp
            nabla_b[-l] = delta
            nabla_w[-l] = np.dot(delta,activations[-l-1].transpose())
        return (nabla_b,nabla_w)
    
    def evaluate(self, test_data):
        """Return the number of test inputs for which the neural network outputs
        the correct result. Note that the neural network's output is assumed to
        be the index of whichever neuron in the final layer has the highest 
        activation"""
        
        test_results = [(np.argmax(self.feedforward(x)),y)
                        for(x,y) in test_data]
        return sum(int(x==y) for (x,y) in test_results)
    
    def cost_derivative(self,output_activations,y):
        """Return the vector of partial derivatives \partial C_x /
        \partial a for the out activations."""
        return(output_activations-y)
    
    def sigmoid_prime(self,z): 
        """Derivative of the sigmoid function"""
        return self.sigmoid(z)*(1-self.sigmoid(z))