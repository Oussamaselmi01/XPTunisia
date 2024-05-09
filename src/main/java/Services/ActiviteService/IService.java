package Services.ActiviteService;

import java.util.List;

public interface IService<A>{


    public void addA(A a);

    public void updateA(A a);

    public void deleteA(Integer a);

    List<A> getAll();

    A getOne(int id);


}
