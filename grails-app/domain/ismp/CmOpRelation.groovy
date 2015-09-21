package ismp

class CmOpRelation {

    String controllers
      String actions
      String names
    static mapping = {
     id(generator: 'org.hibernate.id.enhanced.SequenceStyleGenerator', params: [sequence_name: 'seq_cm_oprelation', initial_value: 1])
  }
    static constraints = {
        controllers(unique:'actions')

  }
}
