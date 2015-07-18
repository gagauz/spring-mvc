package org.repetitor.database.dao;

import org.repetitor.database.model.Comment;
import org.repetitor.database.model.Repetitor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentDao extends AbstractDao<Integer, Comment> {

    public List<Comment> findByRepetitor(Repetitor repetitor) {
        return createQuery("select c from Comment c inner join c.author a where c.repetitor=:rep").setEntity("rep", repetitor).list();
    }

    public List<Comment> findVisibleByRepetitor(Repetitor repetitor) {
        return createQuery("select c from Comment c inner join fetch c.author a where c.repetitor=:rep and c.visible=true").setEntity("rep", repetitor)
                .list();
    }

}
