# mvp-spring
Opinionated (Model)-View-Presenter implementation for Spring.

## Introduction
As a greenhorn developer, I've searched for a satisfying implementation of the Model-View-Presenter Pattern which I could use with Spring. This proved a task harder than expected; in the end, I decided I could use thaose efforts in making an implementation myself.

## Use guide
The core interfaces are Presenter<M, V, P> (M being an unconstrained model class) and View<V, P>. The generic usage aims to enforce correct referencing, giving compile-time errors instead of finding wrong references or DI errors.

Presenter includes a getter and setter for a parent presenter, in order to support a presenter hierarchy (the existence of a main presenter with any other presenter as its child, for example).

AbstractPresenter and AbstractView are (duh) abstract implementations for such interfaces, having references to each other in a prototype-safe way. However, any implementation of the View interface should extend a graphic component; since Java doesn't support multiple inheritance, this means that if you want an abstract view class for the UI framework you intend to use, you have to create it. 
If you use AbstractPresenter, the only rule for the view implementation is that it must have an accessible field of the appropriate presenter type, named "presenter"; note this can be either a private field in the class itself, or a protected one in a superclass. Not complying to this will throw an exception when injecting. The aim of this is to ensure access protection for the fields.

## tl;dr
 - Your presenter classes implement Presenter 
 - Your view classes extend View.
 - Optionally, such presenters extends AbstractPresenter. If so, views must have an accessible presenter field named "presenter".
