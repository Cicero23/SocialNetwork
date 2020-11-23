package socialnetwork.repository.file;

import socialnetwork.domain.Entity;
import socialnetwork.domain.validators.ValidationException;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.factory.Factory;
import socialnetwork.repository.memory.InMemoryRepository;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class FileRepository<ID, E extends Entity<ID>> extends InMemoryRepository<ID,E> {
    String fileName;
    Factory<ID,E> factory;
    public FileRepository(String fileName, Validator<E> validator,Factory<ID,E> factory) {
        super(validator);
        this.fileName=fileName;
        this.factory = factory;
        loadData();

    }

    private void loadData(){
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String linie;
            while((linie=br.readLine())!=null){
                E e=factory.extractEntity(linie);
                try {
                    super.save(e);
                }
                catch (ValidationException t){
                    continue;
                }
            }
        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //public abstract E extractEntity(List<String> attributes);
    ///Observatie-Sugestie: in locul metodei template extractEntity, puteti avea un factory pr crearea instantelor entity

    //protected abstract String createEntityAsString(E entity);

    protected void writeToFile(E entity){
        try (BufferedWriter bW = new BufferedWriter(new FileWriter(fileName,true))) {
            String s = factory.createEntityAsString(entity);
            bW.write(s);
            bW.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected void writeToFileAll(){
        try (BufferedWriter bW = new BufferedWriter(new FileWriter(fileName))) {
            for(E e: super.findAll()){
                bW.write(factory.createEntityAsString(e));
                bW.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public E save(E entity){
        E e=super.save(entity);
        if (e==null)
        {
            writeToFile(entity);
        }
        return e;

    }

    @Override
    public E delete(ID id){
        E e = super.delete(id);
        if(e != null) {
            writeToFileAll();
        }
        return e;
    }

    @Override
    public E update(E entity){
        E e = super.update(entity);
        if(e != null) {
            writeToFileAll();
        }
        return e;
    }

}

