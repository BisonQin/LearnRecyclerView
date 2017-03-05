# LearnRecyclerView
Demo of learn RecyclerView.But not just RecyclerView.

##**Include the following**
MVP + okhttp + glide + gson

Thanks for Open Source Demo.[https://github.com/zhaochenpu/RecyclerViewDemo](https://github.com/zhaochenpu/RecyclerViewDemo)

On the basis of this project, I modified some things, such as the replacement of the image loading library, packaging BaseRecyclerViewAdapter, BaseViewHolder, etc., and the use of MVP architecture.

##Some Screenshot
![](http://p1.bqimg.com/567571/c28602e1c984d22f.gif)

![](http://p1.bqimg.com/567571/e7efdaafb6881456.gif)      

![](http://p1.bqimg.com/567571/257c89c5a1c4751b.gif)

##And So On  
1. RecylerView encapsulates the ViewHolder recycling reuse, that is, RecylerView standardized ViewHolder, the preparation of Adapter oriented ViewHolder is no longer View
2. Provides a plug - in experience, a high degree of decoupling, abnormal flexibility, for a Item display RecylerView specially extracted the corresponding class to control the display of Item, so that its scalability is very strong
3. There are many ItemAnimator in it which you can control add and delete animation. 