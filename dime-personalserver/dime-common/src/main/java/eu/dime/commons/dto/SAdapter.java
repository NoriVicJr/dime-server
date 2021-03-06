/*
* Copyright 2013 by the digital.me project (http://www.dime-project.eu).
*
* Licensed under the EUPL, Version 1.1 only (the "Licence");
* You may not use this work except in compliance with the Licence.
* You may obtain a copy of the Licence at:
*
* http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
*
* Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the Licence for the specific language governing permissions and limitations under the Licence.
*/

package eu.dime.commons.dto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import org.apache.log4j.Logger;

@javax.xml.bind.annotation.XmlRootElement
@javax.xml.bind.annotation.XmlAccessorType(XmlAccessType.FIELD)
public class SAdapter extends Entry {

    @javax.xml.bind.annotation.XmlElement(name = "authUrl")
    private String authUrl;
    @javax.xml.bind.annotation.XmlElement(name = "isConfigurable")
    private boolean isConfigurable;
    @javax.xml.bind.annotation.XmlElement(name = "settings")
    private List<SAdapterSetting> settings;
    @javax.xml.bind.annotation.XmlElement(name = "description")
    private String description;
    private String userId = "@me";


    private static final Logger logger = Logger.getLogger(SAdapter.class);

    public SAdapter() {
        super();

        this.type = "serviceadapter";
        this.isConfigurable = false;
        this.settings = new ArrayList<SAdapterSetting>();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }


    public boolean getIsConfigurable() {
        return this.isConfigurable;

    }

    public void setIsConfigurable(boolean isConfigurable) {
        this.isConfigurable = isConfigurable;

    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SAdapterSetting> getSettings() {
        return this.settings;
    }

    public void addSetting(SAdapterSetting setting) {
        this.settings.add(setting);
    }

    public void setSettings(List<SAdapterSetting> settings) {
        this.settings = settings;
    }

    public String exportSettings() {
        Iterator<SAdapterSetting> iter = this.settings.iterator();
        StringBuilder sb = new StringBuilder();
        while (iter.hasNext()) {
            SAdapterSetting setting = iter.next();
            sb.append("name=");
            sb.append(setting.getName());
            sb.append("##");
            sb.append("fieldtype=");
            sb.append(setting.getType());
            sb.append("##");
            sb.append("mandatory=");
            sb.append(setting.getMandatory());
            sb.append("##");
            sb.append("value=");
            sb.append(setting.getValue());
            sb.append("###");
        }
        if (sb.length() > 0) {
            return sb.substring(0, sb.length() - 3);
        } else {
            return "";
        }
    }

    public void importSettings(String rawSettings) {
        this.settings = new ArrayList<SAdapterSetting>();
        if (rawSettings != null && rawSettings.length() > 0) {
            this.setIsConfigurable(true);
            String[] settings = rawSettings.split("###");
            for (int i = 0; i < settings.length; i++) {
                String[] data = settings[i].split("##");
                SAdapterSetting setting = new SAdapterSetting();
                for (int j = 0; j < data.length; j++) {
                    String[] values = data[j].split("=", 2);
                    if (values[0].equals("name")) {
                        setting.setName(values[1]);
                    } else if (values[0].equals("fieldtype")) {
                        setting.setType(values[1]);
                    } else if (values[0].equals("value")) {
                        setting.setValue(values[1]);
                    } else if (values[0].equals("mandatory") && values[1].equals("true")) {
                        setting.setMandatory(true);
                    } else if (values[0].equals("mandatory") && values[1].equals("false")) {
                        setting.setMandatory(false);
                    }
                }
                this.addSetting(setting);
            }
        }
    }

    public void updateSetting(String name, String value) {
        Iterator<SAdapterSetting> iter = this.settings.iterator();
        boolean updated = false;
        while (iter.hasNext()) {
            SAdapterSetting setting = iter.next();
            if (setting.getName().equals(name)) {
                setting.setValue(value);
                updated = true;
            }
        }
        // We don't know what type it is, so default to string
        if (!updated) {
            logger.warn("update call for unknown setting: (name/value) "+name+"/"+value+"\n using string type");
            this.settings.add(new SAdapterSetting(name, true, "string", value));
        }

    }

    public String getSetting(String key) {
        Iterator<SAdapterSetting> iter = this.settings.iterator();
        while (iter.hasNext()) {
            SAdapterSetting setting = iter.next();
            if (setting.getName().equals(key)) {
                return setting.getValue();
            }
        }
        return "";
    }
}
