package vsp.banks.business.discovery;

import static vsp.banks.helper.ObjectHelper.*;
/**
 * Created by alex on 25.11.15.
 */
public class Service {

  private String name;

  private String description;

  private String service;

  private String uri;

  /**
   * Representation of a micro service.
   */
  public Service(String name, String description, String service, String uri) {
    checkNotNull(name, description, service, uri);
    this.name = name;
    this.description = description;
    this.service = service;
    this.uri = uri;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getService() {
    return service;
  }

  public String getUri() {
    return uri;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    Service service1 = (Service) object;
    if (name != null ? !name.equals(service1.name) : service1.name != null) {
      return false;
    }
    if (description != null
        ? !description.equals(service1.description) : service1.description != null) {
      return false;
    }
    if (service != null ? !service.equals(service1.service) : service1.service != null) {
      return false;
    }
    return !(uri != null ? !uri.equals(service1.uri) : service1.uri != null);

  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + (service != null ? service.hashCode() : 0);
    result = 31 * result + (uri != null ? uri.hashCode() : 0);
    return result;
  }
}
